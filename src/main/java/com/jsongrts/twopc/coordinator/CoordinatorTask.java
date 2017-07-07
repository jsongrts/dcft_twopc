package com.jsongrts.twopc.coordinator;

import com.google.common.io.Files;
import com.google.common.util.concurrent.ListenableFuture;
import com.jsongrts.twopc.StatusConstants;
import com.jsongrts.twopc.api.PrepareResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static com.jsongrts.twopc.coordinator.CoordinatorTask.State.*;

/**
 * Created by jiesong on 7/6/17.
 */
class CoordinatorTask {
    enum State {
        INIT,
        SENDING_PREPARE,
        SENDING_COMMIT,
        INIT_SENDING_COMMIT,
        INIT_SENDING_PREPARE
    }

    /**
     * State information
     */
    private final List<Participant> _participants = new ArrayList<>();
    private final HashMap<Integer/*participant port*/, ListenableFuture<?>> _outstandingRpc =
            new HashMap<>();
    private final String _txnId;
    private final String _value;

    private final String _logFilePath;

    private final ReentrantLock _lock = new ReentrantLock();

    private boolean _commit = false;
    private State _state = INIT;

    private volatile boolean _goal = false;


    public boolean reachGoal() {
        return _goal;
    }


    public CoordinatorTask(String txnId,
                           String value,
                           int[] participantPorts,
                           String logFilePath) throws IOException {
        _txnId = txnId;
        _value = value;
        _logFilePath = logFilePath;
        for (int port : participantPorts) {
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext(true).build();
            _participants.add(new Participant(channel, port));
        }

        File logFile = new File(logFilePath);
        if (logFile.exists()) {
            String a = Files.toString(logFile, Charset.forName("UTF-8")).trim();
            _commit = a.equalsIgnoreCase("commit");
            _state = INIT_SENDING_COMMIT;
        }
        else {
            _state = INIT_SENDING_PREPARE;
        }
    }


    public void applyRule() throws IOException {
        if (_state == INIT_SENDING_PREPARE) {
            _participants.forEach(p -> _outstandingRpc.put(p.getPort(), p.prepare(_txnId, _value)));
            _state = SENDING_PREPARE;
        }
        else if (_state == INIT_SENDING_COMMIT) {
            _participants.forEach(p -> _outstandingRpc.put(p.getPort(), p.commit(_txnId, _commit)));
            _state = SENDING_COMMIT;
        }
        else if (_state == SENDING_PREPARE) {
            if (_checkAllPrepareCompleted()) {
                _commit = _getAggregateVote();
                _outstandingRpc.clear();
                _writeLog();
                _state = SENDING_COMMIT;
                _participants.forEach(p -> _outstandingRpc.put(p.getPort(), p.commit(_txnId, _commit)));
            }
        }
        else if (_state == SENDING_COMMIT) {
            boolean allCompleted = _checkAllCommitCompleted();
            if (allCompleted) {
                _deleteLog();
                _participants.forEach(p -> p.closeChannel());
                _goal = true;
            }
        }
    }


    /**
     * Scan all outstanding prepare rpcs. If there is failed RPC, it will be resent.
     */
    private boolean _checkAllPrepareCompleted() {
        boolean allCompleted = true;

        for (Participant p : _participants) {
            ListenableFuture future = _outstandingRpc.get(p.getPort());
            if (!future.isDone()) {
                allCompleted = false;
                continue;
            }

            try {
                future.get();
            }
            catch (Exception e) {
                allCompleted = false;
                _outstandingRpc.put(p.getPort(), p.prepare(_txnId, _value));
            }
        }

        return allCompleted;
    }


    private boolean _getAggregateVote() {
        try {
            for (ListenableFuture future : _outstandingRpc.values()) {
                PrepareResponse res = (PrepareResponse) future.get();
                if (res.getStatus() != StatusConstants.YES)
                    return false;
            }

            return true;
        }
        catch (Exception e) {
            throw new IllegalStateException("A completed RPC throws an exception");
        }
    }


    private boolean _checkAllCommitCompleted() {
        boolean allCompleted = true;

        for (Participant p : _participants) {
            ListenableFuture future = _outstandingRpc.get(p.getPort());
            if (!future.isDone()) {
                allCompleted = false;
                continue;
            }

            try {
                future.get();
            }
            catch (Exception e) {
                allCompleted = false;
                _outstandingRpc.put(p.getPort(), p.commit(_txnId, _commit));
            }
        }

        return allCompleted;
    }


    private void _deleteLog() {
        File logFile = new File(_logFilePath);
        logFile.delete();
    }

    private void _writeLog() throws IOException {
        _deleteLog();
        File logFile = new File(_logFilePath);
        logFile.createNewFile();
        Files.append(_commit ? "commit" : "abort", logFile, Charset.forName("UTF-8"));
    }
}
