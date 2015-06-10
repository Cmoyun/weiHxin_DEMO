package com.sinaapp.moyun.weixin.service;

import com.sinaapp.moyun.weixin.bean.User;
import com.sinaapp.moyun.weixin.dao.UserDao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;

/**
 * Created by Moy on 六月07  007.
 */
@IocBean
public class UserService extends BaseService {

    @Inject
    private UserDao userDao;

    // 检查并保持用户openId
    public User check(String openId) {
        return userDao.check(openId);
    }

    // 查询音乐指针
    public String getMusicPos(String openId) {
        return userDao.findMusicPos(openId);
    }
    // 设置音乐指针
    public int setMusicPos(String openId, String music_pos) {
        return userDao.setMusicPos(openId, music_pos);
    }
    // 设置文章指针
    public int setArtPos(String openId, String music_pos) {
        return userDao.setArticlePos(openId, music_pos);
    }
    // 查询文章指针
    public String getArticlePos(String openId) {
        return userDao.findArticlePos(openId);
    }
    // 用户锁
    public int setClock(String openId, boolean isClock) {
        return userDao.setLock(openId, isClock);
    }
    // 获得所有用户
    public List<User> list() {
        return userDao.list();
    }
}
