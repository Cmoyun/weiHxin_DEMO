package com.sinaapp.moyun.weixin.util.wx;

import com.sinaapp.moyun.weixin.util.log.Hc;

import java.net.URL;

/**
 * Created by Moy on 六月08  008.
 */
public class SecTest{

    public static void main(String[] args) {
        URL url = Object.class.getClass().getResource("/");
        Hc.v(url);
    }

}