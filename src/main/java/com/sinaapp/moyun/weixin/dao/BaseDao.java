package com.sinaapp.moyun.weixin.dao;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;

/**
 * Created by Moy on 六月07  007.
 */
public class BaseDao<T> {

    @Inject
    protected Dao dao;

    public Dao getDao() {
        return dao;
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }
}
