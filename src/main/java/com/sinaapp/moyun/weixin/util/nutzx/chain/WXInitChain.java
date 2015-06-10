package com.sinaapp.moyun.weixin.util.nutzx.chain;

import com.sinaapp.moyun.weixin.handler.*;
import com.sinaapp.moyun.weixin.interceptor.CheckUserInterceptor;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.impl.processor.AbstractProcessor;

/**
 * Created by Moy on 六月08  008.
 */
@IocBean(singleton = false)
@InjectName("wXInitChain")
public class WXInitChain extends AbstractProcessor {

    @Inject("wxRouterObj")
    private WxMpMessageRouter wxRouterObj;

    @Inject
    private CheckUserInterceptor checkUserInterceptor;

    @Inject
    private MusicHandler musicHandler;
    @Inject
    private ArticleHandler articleHandler;
    @Inject
    private TextHandler textHandler;
    @Inject
    private HcHandler hcHandler;
    @Inject
    private AllHandler allHandler;
    @Inject
    private UrlHandler urlHandler;

    @Override
    public void process(ActionContext ac) throws Throwable {

        wxRouterObj.rule().async(false)
                .interceptor(checkUserInterceptor).next()
        .rule().async(false) //管理员权限
                .rContent("^(hc:)([\\s\\S]*)").handler(hcHandler).end()
        .rule().async(false) //共有权限
                .rContent("^(ls)([\\s\\S]*)").handler(allHandler).end()
        .rule().async(false)
                .rContent("^:(music|歌|音乐|prev|next|jump)([\\s\\S]*)").handler(musicHandler).end()
        .rule().async(false)
                .rContent("^::(article|文章|prev|next|jump|go|lk|now)([\\s\\S]*)").handler(articleHandler).end()
        .rule().async(false)
                .rContent("^(3w)([\\s\\S]*)").handler(urlHandler).end()
        .rule().async(false)
                .handler(textHandler).end();
        // 用来配置微信消息处理器
        doNext(ac);
    }
}
