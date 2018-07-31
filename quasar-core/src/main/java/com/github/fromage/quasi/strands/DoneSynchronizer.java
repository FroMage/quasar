/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.fromage.quasi.strands;

import java.util.concurrent.TimeUnit;

import com.github.fromage.quasi.fibers.SuspendExecution;

/**
 *
 * @author pron
 */
public class DoneSynchronizer implements Condition {
    public static final DoneSynchronizer instance = new DoneSynchronizer();

    private DoneSynchronizer() {
    }

    @Override
    public Object register() {
        Strand.unpark(Strand.currentStrand());
        return null;
    }

    @Override
    public void unregister(Object registrationToken) {
    }

    @Override
    public void await(int iter) throws InterruptedException, SuspendExecution {
    }

    @Override
    public void await(int iter, long timeout, TimeUnit unit) throws InterruptedException, SuspendExecution {
    }

    @Override
    public void signal() {
    }

    @Override
    public void signalAll() {
    }
}
