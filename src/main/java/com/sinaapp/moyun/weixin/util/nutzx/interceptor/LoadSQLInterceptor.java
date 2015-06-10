package com.sinaapp.moyun.weixin.util.nutzx.interceptor;

import org.nutz.aop.InterceptorChain;
import org.nutz.aop.MethodInterceptor;

/**
 * Created by Moy on 六月10  010.
 */
public class LoadSQLInterceptor implements MethodInterceptor {
    @Override
    public void filter(InterceptorChain chain) throws Throwable {

        chain.doChain();
    }
}
