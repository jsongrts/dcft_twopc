package com.jsongrts.twopc.participant;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.io.Files;
import com.jsongrts.twopc.StatusConstants;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import static com.jsongrts.twopc.participant.StateMachine.State.*;

/**
 * A StateMachine has two files as persistent storage:
 *   - the db file - store the official data. The file just contains a single-line string whose format is
 *        $txnId:$value
 *   - the log file - store the logging data. The file just contains a single-line string whose format is
 *        $txnid:$proposedValue:$decision:$state
 */
public class StateMachine {
    public enum State {
        INIT,
        WAIT_FOR_PREPARE,
        WAIT_FOR_COMMIT,
        COMMITING
    }


    private State _state = INIT;

    private String _dbTxnId = null;
    private String _dbValue = null;

    private String _logTxnId = null;
    private String _logValue = null;
    private String _logDecision = null;

    /**
     * $_dbFilePrefix.db -- db file
     * $_dbFilePrefix.log -- log file
     */
    private final String _dir;
    private final String _dbFilePrefix;

    private final ReentrantLock _lock = new ReentrantLock();

    /**
     * Used to mimic the randomness of making decision
     */
    private final Random _rand = new Random(System.currentTimeMillis());


    public StateMachine(final String dir, final String dbFilePrefix) throws IOException {
        _dir = dir;
        _dbFilePrefix = dbFilePrefix;
        _init();
        _loadStateFromDisk();
    }

    /**
     * Check if db file exists, create one otherwise.
     */
    private void _init() throws IOException {
        File dbFile = _createDbFileHandler();
        if (!dbFile.exists()) {
            if (!dbFile.createNewFile())
                throw new IOException("Unable to create the db file - " + dbFile.getPath());
            Files.append("1:hello", dbFile, Charset.forName("UTF-8"));
            File logFile = _createLogFileHandler();
            logFile.delete();
        }
    }

    /**
     * Load data from the db file and the log file, and set the corresponding
     * state.
     */
    private void _loadStateFromDisk() throws IOException {
        File logFile = _createLogFileHandler();
        File dbFile = _createDbFileHandler();

        _loadDb(dbFile);

        if (!logFile.exists()) {
            _state = WAIT_FOR_PREPARE;
            return;
        }

        // the logic to determine the right state after an unexpected crash
        _loadLog(logFile);
        if (_state == WAIT_FOR_COMMIT) {
            // do nothing
        }
        else if (_state == COMMITING) {
            if (_logDecision.equalsIgnoreCase("commit")) {
                _updateDbWithLog(dbFile);
            }
            if (!logFile.delete()) throw new IOException("Unable to delete the log file");
            _state = WAIT_FOR_PREPARE;
        }
        else
            throw new IOException("Corrupted log file");
    }

    public int prepare(String txnId, String proposedValue) throws IOException {
        _lock.lock();
        try {
            switch (_state) {
                case WAIT_FOR_PREPARE:
                    if (txnId.equalsIgnoreCase(_dbTxnId))
                        return StatusConstants.TXN_ALREADY_PROCESSED;
                    else {
                        _logDecision = _rand.nextInt(10) < 2 ? "yes" : "no";
                        _logTxnId = txnId;
                        _logValue = proposedValue;
                        _state = WAIT_FOR_COMMIT;
                        _writeLog();
                        return _logDecision.equalsIgnoreCase("yes") ? StatusConstants.YES : StatusConstants.NO;
                    }

                case WAIT_FOR_COMMIT:
                    if (txnId.equalsIgnoreCase(_logTxnId))
                        return _logDecision.equalsIgnoreCase("yes") ? StatusConstants.YES : StatusConstants.NO;
                    else
                        return StatusConstants.INVALID_PREPARE;

                default:
                    throw new IllegalStateException("Unknown state " + _state);
            }
        }
        finally {
            _lock.unlock();
        }
    }

    public int commit(String txnId, boolean toCommit) throws IOException {
        _lock.lock();
        try {
            switch (_state)
            {
                case WAIT_FOR_PREPARE:
                    return StatusConstants.OK;

                case WAIT_FOR_COMMIT:
                    if (!txnId.equalsIgnoreCase(_logTxnId))
                        return StatusConstants.INVALID_COMMIT;
                    if (toCommit) {
                        _logDecision = "commit";
                        _state = COMMITING;
                        _writeLog();

                        _updateDbWithLog(_createDbFileHandler());

                        _createLogFileHandler().delete();
                        _state = WAIT_FOR_PREPARE;
                        return StatusConstants.OK;
                    }
                    else { // abort
                        _createLogFileHandler().delete();
                        _state = WAIT_FOR_PREPARE;
                    }

                default:
                    throw new IllegalStateException("Illegal state: " + _state);
            }
        }
        finally {
            _lock.unlock();
        }
    }


    /**
     * Apply the content in the log file to the db file
     */
    private void _updateDbWithLog(final File dbFile) throws IOException {
        Preconditions.checkState(_state == WAIT_FOR_COMMIT || _state == COMMITING);

        _dbTxnId = _logTxnId;
        _dbValue = _logValue;

        if (!dbFile.delete())
            throw new IOException("Unable to delete the db file");
        if (!dbFile.createNewFile())
            throw new IOException("Unable to create the new db file");
        Files.append(String.format("%s:%s", _dbTxnId, _dbValue), dbFile, Charset.forName("UTF-8"));
    }

    private void _writeLog() throws IOException {
        File logFile = _createLogFileHandler();
        logFile.delete();
        if (!logFile.createNewFile())
            throw new IOException("Unable to create log file " + logFile.getPath());

        Files.append(String.format("%s:%s:%s:%s", _logTxnId, _logValue, _logDecision, _fromState(_state)),
                logFile, Charset.forName("UTF-8"));
    }

    private File _createDbFileHandler() {
        return new File(_dir + File.separator + _dbFilePrefix + ".db");
    }

    private File _createLogFileHandler() {
        return new File(_dir + File.separator + _dbFilePrefix + ".log");
    }

    private void _loadDb(final File dbFile) throws IOException {
        String[] tokens = _parseFile(dbFile);
        _dbTxnId = tokens[0];
        _dbValue = tokens[1];
    }

    private void _loadLog(final File logFile) throws IOException {
        String[] tokens = _parseFile(logFile);
        _logTxnId = tokens[0];
        _logValue = tokens[1];
        _logDecision = tokens[2];
        _state = _toState(tokens[3]);
    }

    private String[] _parseFile(final File file) throws IOException {
        String s = Files.toString(file, Charset.forName("UTF-8"));
        final ArrayList<String> arr = new ArrayList<>();
        Iterator<String> iter = Splitter.on(':').trimResults().split(s).iterator();
        iter.forEachRemaining(str -> arr.add(str));
        return arr.toArray(new String[arr.size()]);
    }

    private State _toState(final String s) {
        if (s.equalsIgnoreCase("init"))
            return INIT;
        if (s.equalsIgnoreCase("waitforcommit"))
            return WAIT_FOR_COMMIT;
        if (s.equalsIgnoreCase("waitforprepare"))
            return WAIT_FOR_PREPARE;
        if (s.equalsIgnoreCase("commiting"))
            return COMMITING;
        throw new IllegalArgumentException("Unknown state " + s);
    }

    private String _fromState(final State s) {
        switch (s)
        {
            case INIT: return "init";
            case WAIT_FOR_COMMIT: return "waitforcommit";
            case WAIT_FOR_PREPARE: return "waitforprepare";
            case COMMITING: return "commiting";
            default:
                throw new IllegalArgumentException("Unknown state " + s);
        }
    }
}
