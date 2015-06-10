package com.sinaapp.moyun.weixin.service;

import com.sinaapp.moyun.weixin.bean.Music;
import com.sinaapp.moyun.weixin.dao.MusicDao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;

/**
 * Created by Moy on 六月09  009.
 */
@IocBean
public class MusicService extends BaseService {

    @Inject
    private MusicDao musicDao;

    public Music getMusicById(String id) {
        return musicDao.findMusicById(id);
    }

    public List<Music> getTitleList() {
        return musicDao.getTitleList();
    }

}
