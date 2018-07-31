/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.fromage.quasi.fibers.instrument;

import static org.junit.Assert.*;
import org.junit.Test;

import com.github.fromage.quasi.fibers.Fiber;
import com.github.fromage.quasi.fibers.SuspendExecution;
import com.github.fromage.quasi.fibers.TestsHelper;
import com.github.fromage.quasi.strands.SuspendableRunnable;


/**
 *
 * @author Matthias Mann
 */
public class DoubleTest implements SuspendableRunnable {

    double result;

    @Test
    public void testDouble() {
        Fiber co = new Fiber((String)null, null, this);
        TestsHelper.exec(co);
        assertEquals(0, result, 1e-8);
        boolean res = TestsHelper.exec(co);
        assertEquals(1, result, 1e-8);
        assertEquals(res, true);
    }

    @Override
    public void run() throws SuspendExecution {
        double temp = Math.cos(0);
        Fiber.park();
        this.result = temp;
    }

}
