package com.sinaapp.moyun.weixin.util.log;


import com.sinaapp.moyun.weixin.util.log.Log.Color;
import com.sinaapp.moyun.weixin.util.log.util.StackTrace;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Moy on 六月06  006.
 */
public class Hc {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 打印级别
    enum Level{
        V, // 普通级别
        W, // 警告级别
        E // 错误级别
    }

    private static StringBuilder formatString(String tagName, String content, Level level, boolean visit) {
        tagName = (tagName==null||"".equals(tagName))? "None":tagName;
        String bgColor;
        String levelTx = level.toString();
        switch (level) {
            case W:
                bgColor = Color.BG_PURPLE;
                break;
            case E:
                bgColor = Color.BG_RED;
                break;
            default:
                bgColor = "";
        }
        StackTraceElement caller = StackTrace.getCallerStackTraceElement();
        Class cc = null;
        try {
            cc = Class.forName(caller.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = (new StringBuilder("\n- # 【MoLog】  -------- ")).append(sdf.format(new Date())).append(" ------------------# ").append(bgColor).append(Color.F_WHITE).append("【级别:").append(levelTx).append("】").append(Color.NONE).append("\n");
        sb.append(Color.F_GREEN).append("■").append(Color.NONE).append(Color.BORDER).append("Class  : ").append(Color.NONE).append(cc.getName()).append(".(").append(cc.getSimpleName()).append(".java:").append(caller.getLineNumber()).append(")");
        sb.append(Color.F_BLUE).append("\n■").append(Color.NONE).append(Color.BORDER).append("Method : ").append(Color.NONE).append(caller.getMethodName()).append("\n");
        sb.append(Color.F_2GREEN).append("■").append(Color.NONE).append(Color.BORDER).append("Tag    : ").append(Color.NONE).append(tagName).append("\n").append(content).append("\n");
        sb.append("--------------------------------------------------------------------------\n");
        if (visit) {
            String groupName = StackTrace.getThreadGroup().getName();
            String nowName = StackTrace.getThreadName();
            Long nowId = StackTrace.getThreadId();
            int priority = StackTrace.getPriority();
            sb.append(Color.F_YELLOW).append("■").append(Color.NONE).append(Color.BORDER).append("线程信息 : ").append(Color.NONE).append(groupName).append(">").append(nowName).append(":").append(nowId).append("  ")
                    .append(Color.F_YELLOW).append("■").append(Color.NONE).append(Color.BORDER).append("级别:").append(Color.NONE).append(priority).append(Color.NONE);
        }
        return sb;
    }

    private static String buildInfo(Object obj){
        return null == obj? "null":obj.toString();
    }

    //    V级别
    public static void v(String tagName, Object obj, boolean ThreadInfo) {
        System.out.print(formatString(tagName, buildInfo(obj), Level.V, ThreadInfo));
    }
    public static void v(String tagName, Object obj) {
        System.out.print(formatString(tagName, buildInfo(obj), Level.V, false));
    }
    public static void v(Object obj) {
        System.out.print(formatString(null, buildInfo(obj), Level.V, false));
    }

    //    E级别
    public static void e(String tagName, Object obj, boolean ThreadInfo) {
        System.out.print(formatString(tagName, buildInfo(obj), Level.E, ThreadInfo));
    }
    public static void e(String tagName, Object obj) {
        System.out.print(formatString(tagName, buildInfo(obj), Level.E, false));
    }
    public static void e(Object obj) {
        System.out.print(formatString(null, buildInfo(obj), Level.E, false));
    }

    //    W级别
    public static void w(String tagName, Object obj, boolean ThreadInfo) {
        System.out.print(formatString(tagName, buildInfo(obj), Level.W, ThreadInfo));
    }
    public static void w(String tagName, Object obj) {
        System.out.print(formatString(tagName, buildInfo(obj), Level.W, false));
    }
    public static void w(Object obj) {
        System.out.print(formatString(null, buildInfo(obj), Level.W, false));
    }

}
