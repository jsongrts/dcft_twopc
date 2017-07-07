package com.jsongrts.twopc.logger;

import org.apache.logging.log4j.message.Message;

/**
 * Created by jiesong on 7/4/17.
 */
public class LCMessage implements Message {
    private final String _msg;
    private final Throwable _ex;
    private final Object[] _params;

    public LCMessage(String msg) {
        this(msg, null, null);
    }

    public LCMessage(String msg, Throwable ex) {
        this(msg, ex, null);
    }

    public LCMessage(String msg, Object... params) {
        this(msg, null, params);
    }

    public LCMessage(String msg, Throwable ex, Object... params) {
        _msg = msg;
        _ex = ex;
        _params = params;
    }

    @Override
    public String getFormattedMessage() {
        if (_params == null || _params.length == 0)
            return _msg;

        StringBuilder sb = new StringBuilder(_msg);
        sb.append(", params={");
        for (int i = 0; i < _params.length/2; i++) {
            sb.append(_params[2*i].toString()).append('=').append(_params[2*i+1].toString());
            if (i < _params.length/2 - 1)
                sb.append(", ");
        }
        sb.append("}");

        return sb.toString();
    }

    @Override
    public String getFormat() {
        return null;
    }

    @Override
    public Object[] getParameters() {
        return null;
    }

    @Override
    public Throwable getThrowable() {
        return _ex;
    }
}