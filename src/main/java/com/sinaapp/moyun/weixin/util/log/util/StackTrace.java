package com.sinaapp.moyun.weixin.util.log.util;

/**
 * Created by Moy on ����06  006.
 */
public class StackTrace {
    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    public static Long getThreadId() {
        return Thread.currentThread().getId();
    }

    public static int getPriority() {
        return Thread.currentThread().getPriority();
    }

    public static String getThreadName() {
        return Thread.currentThread().getName();
    }

    public static ThreadGroup getThreadGroup() {
        return Thread.currentThread().getThreadGroup();
    }
}
