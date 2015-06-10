package com.sinaapp.moyun.weixin.service;

import com.sinaapp.moyun.weixin.bean.Url;
import com.sinaapp.moyun.weixin.dao.UrlDao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;

/**
 * Created by Moy on 六月10  010.
 */
@IocBean
public class UrlService extends BaseService {

    @Inject
    private UrlDao urlDao;

    public Url getUrlByName(String name) {
        return urlDao.getUrlByName(name);
    }

    public List<Url> getNameList() {
        return urlDao.getNameList();
    }
}
