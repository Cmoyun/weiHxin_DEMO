package com.sinaapp.moyun.weixin.handler;

import com.sinaapp.moyun.weixin.bean.Music;
import com.sinaapp.moyun.weixin.service.MusicService;
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
import me.chanjar.weixin.mp.bean.WxMpXmlOutMusicMessage;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.Map;

/**
 * Created by Moy on 六月08  008.
 */
@IocBean
public class MusicHandler implements WxMpMessageHandler {

    private final String prev = "^(prev)$"; // 上一首
    private final String next = "^(next)"; // 上一首
    private final String jump = "^(jump)(\\s)*(\\d)*"; // 跳到指定歌曲

    @Inject
    private UserService userService;
    @Inject
    private MusicService musicService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        String[] musicBox; // title,description,hqMusicUrl,musicUrl;  Music表中字段
        String openId = (String) sessionManager.getSession(wxMessage.getFromUserName()).getAttribute(Fieldc.SESSION_USER);
        String content = wxMessage.getContent().replace(":","").trim();
        int regexFlag = Machesc.matchesIndex(new String[]{prev, next, jump}, content);
        Integer music_pos = Integer.valueOf(userService.getMusicPos(openId));
        musicBox = control(regexFlag, content, music_pos);
        WxMpXmlOutMusicMessage oMsg = WxMpXmlOutMessage.MUSIC()
                .title(musicBox[0])
                .description(musicBox[1])
                .hqMusicUrl(musicBox[2])
                .musicUrl(musicBox[3])
                .fromUser(wxMessage.getToUserName())
                .toUser(wxMessage.getFromUserName()).build();
        userService.setMusicPos(openId, musicBox[4]);
        return oMsg;
    }

    private String[] control(int regexIndex, String content, int music_pos) {
        Music music;
        String[] musicBox = new String[5];
        switch (regexIndex) {
            case 1:// 上一首
                music = musicService.getMusicById(String.valueOf(music_pos-1));
                if (music == null) {
                    music = musicService.getMusicById(String.valueOf(music_pos));
                }
                break;
            case 2:// 下一首
                music = musicService.getMusicById(String.valueOf(music_pos+1));
                if (music == null) {
                    music = musicService.getMusicById(String.valueOf(music_pos));
                }
                break;
            case 3:// 跳到X
                String tmpPos = content.replace("jump", "").trim();
                Hc.v("jump", tmpPos);
                music = musicService.getMusicById(tmpPos);
                if (music == null) {
                    music = musicService.getMusicById(String.valueOf(music_pos));
                }
                break;
            default:
                music = musicService.getMusicById(String.valueOf(music_pos));
        }
        musicBox[0] = music.getTitle();
        musicBox[1] = music.getDescription();
        musicBox[2] = music.gethQMusicUrl();
        musicBox[3] = music.getMusicURL();
        musicBox[4] = String.valueOf(music.getId());
        return musicBox;
    }
}
