package me.zuhr.demo.zuul.filter;

import com.netflix.zuul.context.RequestContext;

/**
 * @author zurun
 * @date 2018/4/30 17:20:10
 */
public interface CheckPermission {

    void check(RequestContext ctx);

}
