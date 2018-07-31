/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.fromage.quasi.fibers.instrument;

import org.junit.Test;

import com.github.fromage.quasi.fibers.Fiber;
import com.github.fromage.quasi.fibers.SuspendExecution;
import com.github.fromage.quasi.fibers.TestsHelper;
import com.github.fromage.quasi.strands.SuspendableRunnable;

/**
 *
 * @author Matthias Mann
 */
public class Merge3Test implements SuspendableRunnable {

    public boolean a;
    public boolean b;
    
    @Override
    public void run() throws SuspendExecution {
        if(a) {
            Object[] arr = new Object[2];
            System.out.println(arr);
        } else {
            float[] arr = new float[3];
            System.out.println(arr);
        }
        blub();
        System.out.println();
    }
    
    private void blub() throws SuspendExecution {
    }
    
    @Test
    public void testMerge3() {
        Fiber c = new Fiber((String)null, null, new Merge3Test());
        TestsHelper.exec(c);
    }
}
