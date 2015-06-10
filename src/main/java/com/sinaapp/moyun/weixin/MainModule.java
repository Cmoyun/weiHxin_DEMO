package com.sinaapp.moyun.weixin;

import com.sinaapp.moyun.weixin.util.nutzx.setup.InitDBSetup;
import org.nutz.mvc.annotation.ChainBy;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

// 加载bean
@IocBy(type = ComboIocProvider.class,
args = {
        "*js", "conf/ioc",
        "*anno", "com.sinaapp.moyun.weixin",
        "*tx"
})
@SetupBy(value = InitDBSetup.class) // 启动初始化数据库
@ChainBy(args= "conf/mvc/mvc-chain.js") // 初始化微信消息控制器
@Modules(scanPackage = true)
public class MainModule {
}
