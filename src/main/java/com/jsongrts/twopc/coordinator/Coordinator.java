package com.jsongrts.twopc.coordinator;

import java.io.IOException;

/**
 * Created by jiesong on 7/5/17.
 */
public class Coordinator {
    private CoordinatorTask _task;

    public Coordinator(String txnId,
                       String value,
                       int[] participantPorts,
                       String logFilePath) throws IOException {
        _task = new CoordinatorTask(txnId, value, participantPorts, logFilePath);
    }

    public void start() throws IOException {
        while (!_task.reachGoal())
            _task.applyRule();
    }
}

