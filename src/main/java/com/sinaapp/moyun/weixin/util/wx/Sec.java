package com.sinaapp.moyun.weixin.util.wx;

import com.sinaapp.moyun.weixin.util.hc.http.Get;
import org.nutz.json.Json;
import org.nutz.lang.Lang;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Moy on 六月08  008.
 */
public class Sec {

    /**
     * 微信接入校验
     * @param arr 排序的数组
     * @param token token
     * @param signature signature
     * @return 是否通过
     */
    public static boolean checkSignature(String[] arr, String token, String signature) {
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
        }
        return Lang.sha1(sb.toString()).equals(signature);
    }

    /**
     * 获得 微信的AccessToken
     * @param appId
     * @param appSecret
     * @return
     */
    public static String getAccessToken(String appId, String appSecret) {
        String url = String.format(Url4WX.wx_getAccessToken, appId, appSecret);
        String message = Get.getMsg(url);
        HashMap demoJson = Json.fromJson(HashMap.class, Lang.inr(message));
        return (String) demoJson.get("access_token");
    }

    /**
     * 获得 微信的ip
     * @param appId
     * @param appSecret
     * @return
     */
    public static String getWX_IP(String appId, String appSecret) {
        String url = String.format(Url4WX.wx_getWX_IP, getAccessToken(appId, appSecret));
        String message = Get.getMsg(url);
        return message;
    }



}
