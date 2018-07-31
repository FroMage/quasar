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
package com.github.fromage.quasi.strands.channels;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.github.fromage.quasi.fibers.SuspendExecution;
import com.github.fromage.quasi.strands.Timeout;
import com.github.fromage.quasi.strands.channels.Channels.OverflowPolicy;
import com.github.fromage.quasi.strands.queues.BasicSingleConsumerLongQueue;

/**
 *
 * @author pron
 */
public class QueueLongChannel extends QueuePrimitiveChannel<Long> implements LongChannel {
    public QueueLongChannel(BasicSingleConsumerLongQueue queue, OverflowPolicy policy) {
        super(queue, policy);
    }

    @Override
    public long receiveLong() throws SuspendExecution, InterruptedException, EOFException {
        checkClosed();
        awaitItem();
        final long m = queue().pollLong();
        signalSenders();
        return m;
    }

    @Override
    public long receiveLong(long timeout, TimeUnit unit) throws SuspendExecution, InterruptedException, TimeoutException, EOFException {
        checkClosed();
        if (!awaitItem(timeout, unit))
            throw new TimeoutException();
        final long m = queue().pollLong();
        signalSenders();
        return m;
    }

    @Override
    public long receiveLong(Timeout timeout) throws SuspendExecution, InterruptedException, TimeoutException, EOFException {
        return receiveLong(timeout.nanosLeft(), TimeUnit.NANOSECONDS);
    }

    @Override
    public boolean trySend(long message) {
        if (isSendClosed())
            return true;
        if (queue().enq(message)) {
            signalReceivers();
            return true;
        } else
            return false;
    }

    @Override
    public void send(long message) throws SuspendExecution, InterruptedException {
        if (isSendClosed())
            return;
        if (!queue().enq(message))
            super.send(message);
        else
            signalReceivers();
    }

    @Override
    public boolean send(long message, long timeout, TimeUnit unit) throws SuspendExecution, InterruptedException {
        if (isSendClosed())
            return true;
        if (!queue().enq(message))
            return super.send(message, timeout, unit);
        signalReceivers();
        return true;
    }

    @Override
    public boolean send(long message, Timeout timeout) throws SuspendExecution, InterruptedException {
        return send(message, timeout.nanosLeft(), TimeUnit.NANOSECONDS);
    }

    @Override
    protected BasicSingleConsumerLongQueue queue() {
        return (BasicSingleConsumerLongQueue) queue;
    }
}
