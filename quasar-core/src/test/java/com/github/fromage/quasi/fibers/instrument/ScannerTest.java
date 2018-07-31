/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.fromage.quasi.fibers.instrument;

import org.junit.Test;

import com.github.fromage.quasi.common.reflection.ClassLoaderUtil;
import com.github.fromage.quasi.fibers.instrument.SimpleSuspendableClassifier;

import static org.junit.Assert.*;

/**
 *
 * @author eitan
 */
public class ScannerTest {

    @Test
    public void suspendablesMethodWithDescsTest() {
        String path = ClassLoader.getSystemClassLoader().getResource("suspendables.test").getPath();
        SimpleSuspendableClassifier ssc = new SimpleSuspendableClassifier(path);
        final String name = ClassLoaderUtil.classToSlashed("com.github.fromage.quasi.fibers.instrument.ScannerTest");
        assertEquals(true, ssc.isSuspendable(name, "foo", "V"));
        assertEquals(true, ssc.isSuspendable(name, "bar", "(I)V"));
        assertEquals(false, ssc.isSuspendable(name, "bar", "(D)V"));
    }

    @Test
    public void suspendablesClassesTest() {
        String path = ClassLoader.getSystemClassLoader().getResource("suspendables.test").getPath();
        SimpleSuspendableClassifier ssc = new SimpleSuspendableClassifier(path);
        final String name = ClassLoaderUtil.classToSlashed("com.github.fromage.quasi.fibers.instrument.ScannerTestB");
        assertEquals(true, ssc.isSuspendable(name, "anyMethod", "V"));
    }
}
