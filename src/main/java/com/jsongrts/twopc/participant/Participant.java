package com.jsongrts.twopc.participant;

import com.jsongrts.twopc.api.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

/**
 * Created by jiesong on 7/5/17.
 */
public class Participant {
    private final StateMachine _sm;
    private Server _server;

    private int _port;

    public Participant(int port, String dir, String dbPrefix) throws IOException {
        _sm = new StateMachine(dir, dbPrefix);
        _server = ServerBuilder.forPort(port)
                .addService(new TwopcParticipantImpl(_sm))
                .build();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                Participant.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    public void start() throws InterruptedException, IOException {
        _server.start();
    }

    public void blockUntilTermination() throws InterruptedException {
        if (_server != null)
            _server.awaitTermination();
    }

    public void stop() {
        if (_server != null)
            _server.shutdown();
    }
}
