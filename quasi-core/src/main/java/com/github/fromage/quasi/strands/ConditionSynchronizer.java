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
package com.github.fromage.quasi.strands;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.github.fromage.quasi.common.monitoring.FlightRecorder;
import com.github.fromage.quasi.common.monitoring.FlightRecorderMessage;
import com.github.fromage.quasi.common.util.Debug;
import com.github.fromage.quasi.fibers.Fiber;
import com.github.fromage.quasi.fibers.SuspendExecution;

/**
 *
 * @author pron
 */
public abstract class ConditionSynchronizer implements Condition {
    private static final boolean MP = Runtime.getRuntime().availableProcessors() > 1;
    private static final int SPINS = MP ? 1 << 6 : 0;
    final Object owner;

    public ConditionSynchronizer(Object owner) {
        this.owner = owner != null ? owner : this;
    }

    @Override
    public void await(int iter) throws InterruptedException, SuspendExecution {
        final int spins;
        final Fiber<?> fib = Fiber.currentFiber();
        if (fib != null) {
            spins = 0;
            if (iter > 0)
                fib.getMonitor().spuriousWakeup();
        } else
            spins = SPINS - iter;

        if (spins > 0) {
            if (ThreadLocalRandom.current().nextInt(SPINS) == 0)
                Strand.yield();
        } else {
            if (isRecording())
                record("await", "%s parking", Strand.currentStrand());
            Strand.park(owner);
            if (isRecording())
                record("await", "%s awoke", Strand.currentStrand());
        }

        if (Strand.interrupted())
            throw new InterruptedException();
    }

    public void awaitNanos(int iter, long timeoutNanos) throws InterruptedException, SuspendExecution {
        final int spins;
        final Fiber<?> fib = Fiber.currentFiber();
        if (fib != null) {
            spins = 0;
            if (iter > 0)
                fib.getMonitor().spuriousWakeup();
        } else
            spins = SPINS - iter;

        if (spins > 0) {
            if (ThreadLocalRandom.current().nextInt(SPINS) == 0)
                Strand.yield();
        } else {
            if (isRecording())
                record("await", "%s parking", Strand.currentStrand());
            Strand.parkNanos(owner, timeoutNanos);
            if (isRecording())
                record("await", "%s awoke", Strand.currentStrand());
        }

        if (Strand.interrupted())
            throw new InterruptedException();
    }

    @Override
    public void await(int iter, long timeout, TimeUnit unit) throws InterruptedException, SuspendExecution {
        awaitNanos(iter, unit.toNanos(timeout));
    }
    ////////////////////////////
    public static final FlightRecorder RECORDER = Debug.isDebug() ? Debug.getGlobalFlightRecorder() : null;

    boolean isRecording() {
        return RECORDER != null;
    }

    static void record(String method, String format) {
        if (RECORDER != null)
            RECORDER.record(1, new FlightRecorderMessage("ConditionSynchronizer", method, format, null));
    }

    static void record(String method, String format, Object arg1) {
        if (RECORDER != null)
            RECORDER.record(1, new FlightRecorderMessage("ConditionSynchronizer", method, format, new Object[]{arg1}));
    }

    static void record(String method, String format, Object arg1, Object arg2) {
        if (RECORDER != null)
            RECORDER.record(1, new FlightRecorderMessage("ConditionSynchronizer", method, format, new Object[]{arg1, arg2}));
    }

    static void record(String method, String format, Object arg1, Object arg2, Object arg3) {
        if (RECORDER != null)
            RECORDER.record(1, new FlightRecorderMessage("ConditionSynchronizer", method, format, new Object[]{arg1, arg2, arg3}));
    }

    static void record(String method, String format, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (RECORDER != null)
            RECORDER.record(1, new FlightRecorderMessage("ConditionSynchronizer", method, format, new Object[]{arg1, arg2, arg3, arg4}));
    }

    static void record(String method, String format, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        if (RECORDER != null)
            RECORDER.record(1, new FlightRecorderMessage("ConditionSynchronizer", method, format, new Object[]{arg1, arg2, arg3, arg4, arg5}));
    }
}
