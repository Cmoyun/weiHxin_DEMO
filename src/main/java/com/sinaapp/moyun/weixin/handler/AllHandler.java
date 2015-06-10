package com.sinaapp.moyun.weixin.handler;

import com.sinaapp.moyun.weixin.bean.Article;
import com.sinaapp.moyun.weixin.bean.Music;
import com.sinaapp.moyun.weixin.bean.Url;
import com.sinaapp.moyun.weixin.bean.User;
import com.sinaapp.moyun.weixin.service.ArticleService;
import com.sinaapp.moyun.weixin.service.MusicService;
import com.sinaapp.moyun.weixin.service.UrlService;
import com.sinaapp.moyun.weixin.service.UserService;
import com.sinaapp.moyun.weixin.util.hc.http.Machesc;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Moy on 六月10  010.
 */
@IocBean
public class AllHandler implements WxMpMessageHandler {
    @Inject
    private UserService userService;
    @Inject
    private MusicService musicService;
    @Inject
    private ArticleService articleService;
    @Inject
    private UrlService urlService;

    private final String article = "^(a)$"; // article
    private final String music = "^(m)$"; // music
    private final String url = "^(url)$"; // music
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        String openId = (String) sessionManager.getSession(wxMessage.getFromUserName()).getAttribute(Fieldc.SESSION_USER);
        User user = userService.check(openId);
        String tmpContent = wxMessage.getContent().trim().replace("ls", "").trim();
        int val = Machesc.matchesIndex(new String[]{article, music, url}, tmpContent);
        List<String> lsBox = control(val, tmpContent, user.getOpenId());

        StringBuffer rs = new StringBuffer("");
        for (String ls : lsBox) {
            rs.append(ls+"\n");
        }

        WxMpXmlOutTextMessage oMsg = WxMpXmlOutMessage.TEXT()
                .content(rs.toString())
                .fromUser(wxMessage.getToUserName())
                .toUser(wxMessage.getFromUserName())
                .build();

        return oMsg;
    }

    private List<String> control(int val, String tmpContent, String openId) {
        List<String> rs = new ArrayList<String>();
        switch (val) {
            case 1:
                for (Article art : articleService.getTitleList()){
                    rs.add("【id】【Title】");
                    rs.add("【"+art.getId() +"】"+art.getTitle());
                }
                return rs;
            case 2:
                for (Music music : musicService.getTitleList()){
                    rs.add("【id】【Title】");
                    rs.add("【"+music.getId() +"】"+music.getTitle());
                }
                break;
            case 3:
                for (Url url : urlService.getNameList()){
                    rs.add("【id】【Name】");
                    rs.add("【"+url.getId() +"】"+url.getName());
                }
                break;
            default:
        }
        return rs;
    }
}
