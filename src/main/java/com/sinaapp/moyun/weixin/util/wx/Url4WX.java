package com.sinaapp.moyun.weixin.util.wx;

/**
 * Created by Moy on 六月08  008.
 */
public class Url4WX {
    public static final String wx_getAccessToken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    public static final String wx_getWX_IP = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=%s";
}
