package com.agent;

public class Mytrace {
    private static boolean isIn(String owner) {
        if (owner.startsWith("java")) {
            return false;
        }
        return true;
    }

    public static void traceArrayRead(Object arr, int index) {
        long hashID = System.identityHashCode(arr) + index;
        String arrName = arr.getClass().getCanonicalName().replace("[]", "");
        logArr(1, hashID, arrName, index);
    }

    public static void traceArrayWrite(Object arr, int index) {
        long hashID = System.identityHashCode(arr) + index;
        String arrName = arr.getClass().getCanonicalName().replace("[]", "");
        logArr(0, hashID, arrName, index);
    }


    public static void traceStaticFieldRead(String owner, String name) {
        if (!isIn(owner)) {
            return;
        }
        long hashID = System.identityHashCode(owner) + name.hashCode();
        logStaticAndField(1, hashID, owner, name);
    }

    public static void traceStaticFieldWrite(String owner, String name) {
        if (!isIn(owner)) {
            return;
        }
        long hashID = System.identityHashCode(owner) + name.hashCode();
        logStaticAndField(0, hashID, owner, name);
    }

    public static void traceFieldRead(Object owner, String name) {
        if (!isIn(owner.getClass().getCanonicalName())) {
            return;
        }
        long hashID = System.identityHashCode(owner) + name.hashCode();
        logStaticAndField(1, hashID, owner.getClass().getCanonicalName(), name);
    }

    public static void traceFieldWrite(Object owner, String name) {
        if (!isIn(owner.getClass().getCanonicalName())) {
            return;
        }
        long hashID = System.identityHashCode(owner) + name.hashCode();
        logStaticAndField(0, hashID, owner.getClass().getCanonicalName(), name);
    }


    private static void logArr(int R_W, long hashID, String arrName, int index) {
        if (R_W == 0) {
            System.out.printf("W %d %x %s[%d]\n", Thread.currentThread().getId(), hashID, arrName, index);
        } else {
            System.out.printf("R %d %x %s[%d]\n", Thread.currentThread().getId(), hashID, arrName, index);
        }
    }

    private static void logStaticAndField(int R_W, long hashID, String objectName, String name) {
        if (R_W == 0) {
            System.out.printf("W %d %x %s.%s\n", Thread.currentThread().getId(), hashID, objectName, name);
        } else {
            System.out.printf("R %d %x %s.%s\n", Thread.currentThread().getId(), hashID, objectName, name);
        }
    }


    public static void must() {
    }
}

