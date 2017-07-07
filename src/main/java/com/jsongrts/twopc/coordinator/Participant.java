package com.jsongrts.twopc.coordinator;

import com.google.common.util.concurrent.ListenableFuture;
import com.jsongrts.twopc.api.*;
import io.grpc.ManagedChannel;

/**
 * Created by jiesong on 7/6/17.
 */
class Participant {
    private final ManagedChannel _channel;
    private final int _port;

    public Participant(ManagedChannel channel, int port) {
        _channel = channel;
        _port = port;
    }

    public int getPort() {
        return _port;
    }

    public ListenableFuture<PrepareResponse> prepare(String txnId, String value) {
        PrepareRequest req = PrepareRequest.newBuilder().setTxnId(txnId).setNewValue(value).build();
        TwopcParticipantGrpc.TwopcParticipantFutureStub stub = TwopcParticipantGrpc.newFutureStub(_channel);
        return stub.prepare(req);
    }

    public ListenableFuture<CommitResponse> commit(String txnId, boolean commit) {
        CommitRequest req = CommitRequest.newBuilder().setTxnId(txnId).setCommitOrAbort(commit).build();
        TwopcParticipantGrpc.TwopcParticipantFutureStub stub = TwopcParticipantGrpc.newFutureStub(_channel);
        return stub.commit(req);
    }

    public void closeChannel() {
        _channel.shutdownNow();
    }
}
