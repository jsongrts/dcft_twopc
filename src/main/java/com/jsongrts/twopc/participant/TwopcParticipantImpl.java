package com.jsongrts.twopc.participant;

import com.jsongrts.twopc.api.*;
import com.jsongrts.twopc.logger.LOG;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

/**
 * Created by jiesong on 7/5/17.
 */
class TwopcParticipantImpl extends TwopcParticipantGrpc.TwopcParticipantImplBase  {
    private final StateMachine _sm;

    public TwopcParticipantImpl(final StateMachine sm) {
        _sm = sm;
    }

    @Override
    public void prepare(PrepareRequest req, StreamObserver<PrepareResponse> responseObserver) {
        try {
            int ret = _sm.prepare(req.getTxnId(), req.getNewValue());

            PrepareResponse res = PrepareResponse.newBuilder().setTxnId(req.getTxnId()).setStatus(ret).build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
        }
        catch (IOException e) {
            LOG.logger().error("encounter an exception", e);
        }
    }

    @Override
    public void commit(CommitRequest req, StreamObserver<CommitResponse> responseObserver) {
        try {
            int status = _sm.commit(req.getTxnId(), req.getCommitOrAbort());

            CommitResponse res = CommitResponse.newBuilder().setTxnId(req.getTxnId()).setStatus(status).build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
        }
        catch (IOException e) {
            LOG.logger().error("Encounter an exception", e);
        }
    }

}
