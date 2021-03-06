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
package com.github.fromage.quasi.fibers.instrument;

import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.github.fromage.quasi.fibers.Fiber;
import com.github.fromage.quasi.fibers.SuspendExecution;
import com.github.fromage.quasi.fibers.Suspendable;
import com.github.fromage.quasi.strands.SuspendableCallable;

import static org.junit.Assert.*;

/**
 * A test for issue #73: https://github.com/puniverse/quasi/issues/73
 * @author circlespainter
 */
public class RTInitLocalArrayArgTest implements SuspendableCallable {
    @Suspendable // Instrumentation is needed to break
    private static Object myMethod(Object arg) {
        return arg;
    }

    public Object run() throws SuspendExecution, InterruptedException {
        Object arg = null;

        // Any runtime check needed here to break instrumentation
        if (System.getProperties() != null) {
            arg = new Object[0];
            // This doesn't break
            // arg = new Object();
        }

        // Passing a copy of the args bound to a local is needed to break instrumentation
        return myMethod(arg);
    }

    @Test
    public void test() throws ExecutionException, InterruptedException {
        assertTrue(new Fiber(this).start().get() != null);
    }
}
