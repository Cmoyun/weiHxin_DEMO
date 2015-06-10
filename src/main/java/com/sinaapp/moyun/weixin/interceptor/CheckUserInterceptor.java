package com.sinaapp.moyun.weixin.interceptor;

import com.sinaapp.moyun.weixin.service.UserService;
import com.sinaapp.moyun.weixin.util.log.Hc;
import com.sinaapp.moyun.weixin.util.wx.Fieldc;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSession;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageInterceptor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.Map;

/**
 * Created by Moy on 六月09  009.
 */
@IocBean
public class CheckUserInterceptor implements WxMpMessageInterceptor {

    @Inject
    private UserService userService;

    @Override
    public boolean intercept(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {

        Hc.v("CheckUserInterceptor " + wxMessage.getFromUserName());
        WxSession session = sessionManager.getSession(wxMessage.getFromUserName());
        if (session.getAttribute(Fieldc.SESSION_USER) == null) {
            session.setAttribute(Fieldc.SESSION_USER, userService.check(wxMessage.getFromUserName()).getOpenId());
        }
        return true;
    }
}
