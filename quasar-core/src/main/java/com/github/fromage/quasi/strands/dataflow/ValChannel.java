/*
 * Quasar: lightweight threads and actors for the JVM.
 * Copyright (c) 2013-2014, Parallel Universe Software Co. All rights reserved.
 * 
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *  
 *   or (per the licensee's choosing)
 *  
 * under the terms of the GNU Lesser General Public License version 3.0
 * as published by the Free Software Foundation.
 */
package com.github.fromage.quasi.strands.dataflow;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.fromage.quasi.fibers.SuspendExecution;
import com.github.fromage.quasi.strands.Condition;
import com.github.fromage.quasi.strands.Timeout;
import com.github.fromage.quasi.strands.channels.ReceivePort;
import com.github.fromage.quasi.strands.channels.SelectAction;
import com.github.fromage.quasi.strands.channels.SelectActionImpl;
import com.github.fromage.quasi.strands.channels.Selectable;

/**
 * An adapter that turns a DelayedVal into a ReceivePort that receives the DelayedVal's value and then closes.
 *
 * @author pron
 */
public class ValChannel<V> implements ReceivePort<V>, Selectable<V> {
    private final Val<V> dv;
    private final AtomicBoolean closed = new AtomicBoolean();

    public ValChannel(Val<V> val) {
        this.dv = val;
    }

    @Override
    public V receive() throws SuspendExecution, InterruptedException {
        if (closed.get())
            return null;
        final V v = dv.get();
        close();
        return v;
    }

    @Override
    public V tryReceive() {
        if (closed.get())
            return null;
        if (!dv.isDone())
            return null;
        final V v = dv.getValue();
        close();
        return v;
    }

    @Override
    public V receive(long timeout, TimeUnit unit) throws SuspendExecution, InterruptedException {
        if (closed.get())
            return null;
        final V v;
        try {
            v = dv.get(timeout, unit);
            close();
            return v;
        } catch (TimeoutException ex) {
            return null;
        }
    }

    @Override
    public V receive(Timeout timeout) throws SuspendExecution, InterruptedException {
        return receive(timeout.nanosLeft(), TimeUnit.NANOSECONDS);
        
    }

    @Override
    public void close() {
        closed.set(true);
    }

    @Override
    public boolean isClosed() {
        return closed.get();
    }

    @Override
    public Object register(SelectAction<V> action1) {
        SelectActionImpl<V> action = (SelectActionImpl<V>)action1;
        if (action.isData())
            throw new UnsupportedOperationException("Send is not supported by DelayedValChanel");
        Condition sync = dv.getSync();
        if (sync == null) {
            if (!action.lease())
                return null;
            action.setItem(dv.getValue());
            action.won();
            return null;
        }
        sync.register();
        return action;
    }

    @Override
    public boolean tryNow(Object token) {
        if (!dv.isDone())
            return false;
        SelectActionImpl<V> action = (SelectActionImpl<V>) token;
        if (!action.lease())
            return false;
        action.setItem(dv.getValue());
        action.won();
        return true;
    }

    @Override
    public void unregister(Object token) {
        if (token == null)
            return;
        Condition sync = dv.getSync();
        if (sync != null)
            sync.unregister(null);
    }
}
