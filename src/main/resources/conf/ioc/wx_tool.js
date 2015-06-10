var wx_tool = {

    // 微信tool 的配置文件
    wxConfig: {
        type: "org.nutz.ioc.impl.PropertiesProxy",
        fields: {
            paths: ["conf/WXconfig.properties"]
        }
    },
    // 微信tool 的配置类
    wxConfigObj: {
        type: "me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage",
        fields: {
            appId: {java: "$wxConfig.get('AppID')"},
            secret: {java: "$wxConfig.get('AppSecret')"},
            token: {java: "$wxConfig.get('Token')"},
            aesKey: {java: "$wxConfig.get('EncodingAESKey')"}
        }
    },
    // 微信tool 的服务
    wxServiceObj: {
        type: "me.chanjar.weixin.mp.api.WxMpServiceImpl",
        fields: {
            wxMpConfigStorage: {refer: "wxConfigObj"}
        }
    },
    // 微信tool 的转发路由
    wxRouterObj: {
        type: "me.chanjar.weixin.mp.api.WxMpMessageRouter",
        args: [{refer: "wxServiceObj"}]
    }
};