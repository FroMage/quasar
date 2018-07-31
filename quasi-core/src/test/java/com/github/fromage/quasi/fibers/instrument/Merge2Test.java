/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.fromage.quasi.fibers.instrument;

import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.fromage.quasi.common.util.Exceptions;
import com.github.fromage.quasi.fibers.Fiber;
import com.github.fromage.quasi.fibers.SuspendExecution;
import com.github.fromage.quasi.fibers.TestsHelper;
import com.github.fromage.quasi.strands.Strand;
import com.github.fromage.quasi.strands.SuspendableRunnable;

/**
 *
 * @author mam
 */
public class Merge2Test implements SuspendableRunnable {
    private static Strand.UncaughtExceptionHandler previousUEH;

    @BeforeClass
    public static void setupClass() {
        previousUEH = Fiber.getDefaultUncaughtExceptionHandler();
        Fiber.setDefaultUncaughtExceptionHandler(new Strand.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Strand s, Throwable e) {
                Exceptions.rethrow(e);
            }
        });
    }

    @AfterClass
    public static void afterClass() {
        // Restore
        Fiber.setDefaultUncaughtExceptionHandler(previousUEH);
    }

    public interface Interface {
        public void method();
    }

    public static Interface getInterface() {
        return null;
    }

    public static void suspendable() throws SuspendExecution {
    }

    @Override
    public void run() throws SuspendExecution {
        try {
            Interface iface = getInterface();
            iface.method();
        } catch(IllegalStateException ise) {
            suspendable();
        }
    }

    @Test
    public void testMerge2() {
        try {
            Fiber c = new Fiber((String)null, null, new Merge2Test());
            TestsHelper.exec(c);
            assertTrue("Should not reach here", false);
        } catch (NullPointerException ex) {
            // NPE expected
        }
    }
}
