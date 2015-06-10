package com.sinaapp.moyun.weixin.util.nutzx.interceptor;

import org.nutz.aop.InterceptorChain;
import org.nutz.aop.MethodInterceptor;

/**
 * Created by Moy on 六月08  008.
 */
public class CharacterInterceptor implements MethodInterceptor {
    @Override
    public void filter(InterceptorChain chain) throws Throwable {
        chain.doChain();
    }
}
