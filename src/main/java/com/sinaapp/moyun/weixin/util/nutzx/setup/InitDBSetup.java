package com.sinaapp.moyun.weixin.util.nutzx.setup;

import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

/**
 * Created by Moy on 六月07  007.
 */
public class InitDBSetup implements Setup{
    @Override
    public void init(NutConfig nutConfig) {
        Ioc ioc = nutConfig.getIoc();
        Dao dao = ioc.get(Dao.class);
        Daos.createTablesInPackage(dao, "com.sinaapp.moyun.weixin.bean", false);
    }

    @Override
    public void destroy(NutConfig nutConfig) {

    }
}
