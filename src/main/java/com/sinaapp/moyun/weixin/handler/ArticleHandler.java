package com.sinaapp.moyun.weixin.handler;

import com.sinaapp.moyun.weixin.bean.Article;
import com.sinaapp.moyun.weixin.service.ArticleService;
import com.sinaapp.moyun.weixin.service.UserService;
import com.sinaapp.moyun.weixin.util.hc.http.Machesc;
import com.sinaapp.moyun.weixin.util.wx.Fieldc;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.outxmlbuilder.NewsBuilder;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Moy on 六月09  009.
 */
@IocBean
public class ArticleHandler implements WxMpMessageHandler {
    private final String prev = "^(prev)$"; // 上一篇
    private final String next = "^(next)$"; // 上一篇
    private final String jump = "^(jump)(\\s)*(\\d)*"; // 跳到指定

    private final String go   = "^(go)(\\s)*(\\d)*"; // 获得指定页码的列表
    private final String lk   = "^(lk)([\\s\\S]*)"; // 获得指定匹配的列表
    private final String now  = "^(now)$"; // 获得最新列表

    @Inject
    private UserService userService;
    @Inject
    private ArticleService articleService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        Article artBox;
        String openId = (String) sessionManager.getSession(wxMessage.getFromUserName()).getAttribute(Fieldc.SESSION_USER);
        String content = wxMessage.getContent().replace("::","").trim();
        int regexFlag = Machesc.matchesIndex(new String[]{prev, next, jump}, content);
        Integer art_pos = Integer.valueOf(userService.getArticlePos(openId));
        artBox = controlA(regexFlag, content, art_pos); // !=-1 匹配 prev, next, jump

        ArrayList<WxMpXmlOutNewsMessage.Item> items = new ArrayList<WxMpXmlOutNewsMessage.Item>();

        if (regexFlag != -1) {
            WxMpXmlOutNewsMessage.Item item = bornItem4Article(artBox);
            items.add(item);
            userService.setArtPos(openId, String.valueOf(artBox.getId()));
        }

        regexFlag = Machesc.matchesIndex(new String[]{go, lk, now}, content);

        List<Article> artBoxs = controlB(regexFlag, content, art_pos);
        if (regexFlag != -1) {
            WxMpXmlOutNewsMessage.Item item;
            for (Article article : artBoxs) {
                item = bornItem4Article(article);
                items.add(item);
            }
        }

        NewsBuilder oMsg = WxMpXmlOutMessage.NEWS()
                .fromUser(wxMessage.getToUserName())
                .toUser(wxMessage.getFromUserName());

        for (WxMpXmlOutNewsMessage.Item i : items) {
            oMsg.addArticle(i);
        }
        return oMsg.build();
    }

    private List<Article> controlB(int regexFlag, String content, Integer art_pos) {
        List<Article> articles;
        switch (regexFlag) {
            case 1:// 获得指定页码的列表
                String tmpPos = content.replace("go", "").trim();
                articles = articleService.getPage(Integer.parseInt(tmpPos)).getList(Article.class);
                break;
            case 2:// 获得指定匹配的列表
                tmpPos = content.replace("lk", "").trim();
                articles = articleService.getArticleForLike(tmpPos);
                break;
            case 3:// 获得最新列表
                articles = articleService.getArticleForNow();
                break;
            default:
                articles = new ArrayList<Article>();
                articles.add(articleService.getArticleById(art_pos));
        }
        return articles;
    }

    private Article controlA(int regexIndex, String content, int art_pos) {
        Article article;
        switch (regexIndex) {
            case 1:// 上一首
                article = articleService.getArticleById(art_pos - 1);
                if (article == null) {
                    article = articleService.getArticleById(art_pos);
                }
                break;
            case 2:// 下一首
                article = articleService.getArticleById(art_pos + 1);
                if (article == null) {
                    article = articleService.getArticleById(art_pos);
                }
                break;
            case 3:// 跳到X
                String tmpPos = content.replace("jump", "").trim();
                article = articleService.getArticleById(Integer.parseInt(tmpPos));
                if (article == null) {
                    article = articleService.getArticleById(art_pos);
                }
                break;
            default:
                article = articleService.getArticleById(art_pos);
        }
        return article;
    }

    private WxMpXmlOutNewsMessage.Item bornItem4Article(Article article) {
        WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
        item.setTitle(article.getTitle());
        item.setDescription(article.getDescription());
        item.setPicUrl(article.getPicUrl());
        item.setUrl(article.getUrl());
        return item;
    }
}
