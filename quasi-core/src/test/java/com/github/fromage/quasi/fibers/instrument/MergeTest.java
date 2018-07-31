/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.fromage.quasi.fibers.instrument;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;

import com.github.fromage.quasi.fibers.Fiber;
import com.github.fromage.quasi.fibers.SuspendExecution;
import com.github.fromage.quasi.fibers.TestsHelper;
import com.github.fromage.quasi.strands.SuspendableRunnable;


/**
 *
 * @author mam
 */
public class MergeTest implements SuspendableRunnable {

    public static void throwsIO() throws IOException {
    }

    @Override
    public void run() throws SuspendExecution {
        try {
            throwsIO();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMerge() {
        Fiber c = new Fiber((String)null, null, new MergeTest());
        TestsHelper.exec(c);
    }
}
