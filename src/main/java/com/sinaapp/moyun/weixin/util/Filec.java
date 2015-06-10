package com.sinaapp.moyun.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Moy on 六月08  008.
 */
public class Filec {

    /**
     * 获得 properties 文件的读取对象
     * @param url
     * @return
     */
    public static Properties getProperties(String url){
        Properties prop = new Properties();
        InputStream is = Object.class.getResourceAsStream(url);
        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * 获得 properties 文件的读取对象
     * @param is
     * @return
     */
    public static Properties getProperties(InputStream is){
        Properties prop = new Properties();
        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
