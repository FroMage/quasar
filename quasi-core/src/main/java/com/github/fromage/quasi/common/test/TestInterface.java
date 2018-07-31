package com.github.fromage.quasi.common.test;

import java.util.ArrayList;

import com.github.fromage.quasi.fibers.Suspendable;

public interface TestInterface {
    @Suspendable
    void test(ArrayList<String> results);
}
