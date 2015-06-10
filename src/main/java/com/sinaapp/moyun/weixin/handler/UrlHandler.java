package com.sinaapp.moyun.weixin.handler;

import com.sinaapp.moyun.weixin.bean.Url;
import com.sinaapp.moyun.weixin.service.UrlService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.Map;

/**
 * Created by Moy on 六月10  010.
 */
@IocBean
public class UrlHandler implements WxMpMessageHandler {

    @Inject
    private UrlService urlService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        String textBox;
        String content = wxMessage.getContent().replace("3w","").trim();

        Url url = urlService.getUrlByName(content);
        textBox = url.getUrl() == null? "不存在哦":url.getUrl();

        WxMpXmlOutTextMessage oMsg = WxMpXmlOutMessage.TEXT()
                .content(textBox)
                .fromUser(wxMessage.getToUserName())
                .toUser(wxMessage.getFromUserName())
                .build();
        return oMsg;
    }
}
