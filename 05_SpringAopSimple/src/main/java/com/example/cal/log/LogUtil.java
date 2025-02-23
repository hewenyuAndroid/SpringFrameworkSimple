package com.example.cal.log;

import java.util.Arrays;

public class LogUtil {

    public static void excBefore(String methodName, Object[] args) {
        System.out.println("LogUtil: before(), methodName: " + methodName + ", args: " + Arrays.toString(args));
    }

    public static void excReturn(String methodName, Object result) {
        System.out.println("LogUtil: return(), methodName: " + methodName + ", result: " + result);
    }

    public static void excException(String methodName, Throwable tx) {
        System.out.println("LogUtil: exception(), methodName: " + methodName + ", exception: " + tx.getCause());
    }

    public static void excEnd(String methodName) {
        System.out.println("LogUtil: end(), methodName: " + methodName);
    }

}
