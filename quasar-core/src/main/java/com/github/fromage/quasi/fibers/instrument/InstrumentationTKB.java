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

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.objectweb.asm.util.ASMifier;

import com.github.fromage.quasi.fibers.SuspendExecution;

//
import java.util.*;
import org.objectweb.asm.*;

/**
 *
 * @author pron
 */
public class InstrumentationTKB {
    public static void main(String[] args) throws Exception {
        ASMifier.main(new String[]{"com.github.fromage.quasi.fibers.instrument.InstrumentationTKB"});
    }

    public void foo() throws Throwable {
    }
}
