package com.sinaapp.moyun.weixin.dao;

import com.sinaapp.moyun.weixin.bean.Music;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;

/**
 * Created by Moy on 六月09  009.
 */
@IocBean
public class MusicDao extends BaseDao {

    public Music findMusicById(String id) {
        return getDao().fetch(Music.class, Cnd.where("id", "=", id));
    }

    public List<Music> getTitleList(){
        return getDao().query(Music.class, Cnd.where("1","=","1"));
    }
}
