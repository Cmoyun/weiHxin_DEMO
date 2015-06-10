package com.sinaapp.moyun.weixin.handler;

import com.sinaapp.moyun.weixin.bean.User;
import com.sinaapp.moyun.weixin.service.UserService;
import com.sinaapp.moyun.weixin.util.hc.http.Machesc;
import com.sinaapp.moyun.weixin.util.log.Hc;
import com.sinaapp.moyun.weixin.util.wx.Fieldc;
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
 * Created by Moy on 六月09  009.
 */
@IocBean
public class HcHandler implements WxMpMessageHandler {

    @Inject
    private UserService userService;

    private final String gb = "^(gb)([\\s\\S]*):([\\s\\S]*)"; // 广播
    private final String lock = "^(lock)([\\s\\S]*)"; // lock
    private final String unlock = "^(unlock)([\\s\\S]*)"; // unlock

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        String openId = (String) sessionManager.getSession(wxMessage.getFromUserName()).getAttribute(Fieldc.SESSION_USER);
        User user = userService.check(openId);

        String tmpContent = wxMessage.getContent().trim().replace("hc:", "").trim();

        int powerVal = 0;
        powerVal += user.isClock()? 0: 1;// 是否上锁
        powerVal += user.isPower()? 2: 0;// 是否管理员
        int val = Machesc.matchesIndex(new String[]{gb, lock, unlock}, tmpContent);

        String[] Box = new String[]{openId,"权限不足"};// 别人的box TODO 待验证 哈哈 早点睡
        if (val <= powerVal) {
            String[] tmpBox = control(val, tmpContent, openId);// [0]用户 [1]文字
            if (tmpBox != null) {
                Hc.v("tmpBox", tmpBox[0] +"::"+ tmpBox[1]);
                Box[0] = tmpBox[0].trim();
                Box[1] = tmpBox[1].trim() + "【f_" + openId +"】";
            } else {
                Box[1] = "操作成功";
            }
        }

        WxMpXmlOutTextMessage oMsg = WxMpXmlOutMessage.TEXT()
                .content(Box[1])
                .fromUser(wxMessage.getToUserName())
                .toUser(Box[0])
                .build();
        return oMsg;
    }

    private String[] control(int val, String tmpContent,String openId) {
        switch (val) {
            case 1:// gb
                tmpContent = tmpContent.replace("gb", "").trim();
                return tmpContent.split(":");
            case 2:// lock
                tmpContent = tmpContent.replace("lock", "").trim();
                userService.setClock(tmpContent, true);
                break;
            case 3:// unlock
                tmpContent = tmpContent.replace("unlock", "").trim();
                userService.setClock(tmpContent, false);
                break;
            default:
        }
        return null;
    }
}
