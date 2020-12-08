package com.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

public class PerfMonAgent {
    private static Instrumentation inst = null;
    public static void premain(String agentArgs, Instrumentation _inst) {
        inst = _inst;
        ClassFileTransformer trans = new PerfMonXformer();
        inst.addTransformer(trans);
    }
}
