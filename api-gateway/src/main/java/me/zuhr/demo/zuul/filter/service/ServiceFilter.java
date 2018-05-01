package me.zuhr.demo.zuul.filter.service;

import com.netflix.zuul.context.RequestContext;

/**
 * @author zurun
 * @date 2018/4/30 17:20:10
 */
public interface ServiceFilter {

    /**
     * 判断是否需要鉴权
     *
     * @param ctx
     * @return
     */
    boolean authentication(RequestContext ctx);

}
