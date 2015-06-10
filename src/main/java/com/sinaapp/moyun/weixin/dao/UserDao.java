package com.sinaapp.moyun.weixin.dao;

import com.sinaapp.moyun.weixin.bean.User;
import com.sinaapp.moyun.weixin.util.log.Hc;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;

/**
 * Created by Moy on 六月07  007.
 */
@IocBean
public class UserDao extends BaseDao{

    public int count(Class clazz) {
        return getDao().count(clazz);
    }

    public User check(String openId) {
        User user = getDao().fetch(User.class, Cnd.where("openId", "=", openId));
        if (user == null){
            user = new User();
            user.setOpenId(openId);
            user = getDao().insert(user);
        }
        return user;
    }

    public String findMusicPos(String openId){
        User user = getDao().fetch(User.class, Cnd.where("openId", "=", openId));
        Hc.v(user);
        return user.getMusic_pos();
    }

    public String findArticlePos(String openId){
        User user = getDao().fetch(User.class, Cnd.where("openId", "=", openId));
        return user.getArticle_pos();
    }

    public int setMusicPos(String openId, String changePos) {
        User user = getDao().fetch(User.class, Cnd.where("openId", "=", openId));
        user.setMusic_pos(changePos);
        return getDao().update(user);
    }

    public int setArticlePos(String openId, String changePos) {
        User user = getDao().fetch(User.class, Cnd.where("openId", "=", openId));
        user.setArticle_pos(changePos);
        return getDao().update(user);
    }

    public int setLock(String openId, boolean isLock) {
        User user = getDao().fetch(User.class, Cnd.where("openId", "=", openId));
        user.setClock(isLock);
        return getDao().update(user);
    }

    public List<User> list() {
        return getDao().query(User.class, Cnd.where("1","=","1"));
    }
}
