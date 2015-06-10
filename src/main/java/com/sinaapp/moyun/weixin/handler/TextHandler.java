package com.sinaapp.moyun.weixin.handler;

import com.sinaapp.moyun.weixin.util.log.Hc;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.Map;

/**
 * Created by Moy on 六月09  009.
 */
@IocBean
public class TextHandler implements WxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        Hc.v("TextHandler -- > 开始" + wxMessage.getFromUserName());

        WxMpXmlOutTextMessage oMsg = WxMpXmlOutMessage.TEXT()
                .content("后台正在处理,请稍后")
                .fromUser(wxMessage.getToUserName())
                .toUser(wxMessage.getFromUserName())
                .build();
        Hc.v("TextHandler -- > 结束" + wxMessage.getFromUserName());
        return oMsg;
    }
}
