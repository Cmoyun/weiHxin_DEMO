package com.sinaapp.moyun.weixin.util.hc.http;

import java.util.regex.Pattern;

/**
 * Created by Moy on 六月09  009.
 */
public class Machesc {

    public static int matchesIndex(String[] regex, CharSequence str) {
        for (int i = 0; i < regex.length; i++) {
            if (Pattern.matches(regex[i], str)) {
                return ++i;
            }
        }
        return -1;
    }
}
