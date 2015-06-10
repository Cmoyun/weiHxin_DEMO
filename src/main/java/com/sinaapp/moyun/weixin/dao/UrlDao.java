package com.sinaapp.moyun.weixin.dao;

import com.sinaapp.moyun.weixin.bean.Url;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;

/**
 * Created by Moy on 六月10  010.
 */
@IocBean
public class UrlDao extends BaseDao{

    public Url getUrlByName(String name) {
        return getDao().fetch(Url.class, Cnd.where("name", "=", name));
    }

    public List<Url> getNameList() {
        return getDao().query(Url.class, Cnd.where("1", "=", "1"));
    }
}
