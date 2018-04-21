package me.zuhr.demo.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zurun
 * @date 2018/4/21 22:54:19
 */
public abstract class AbstractZuulFilter extends ZuulFilter {
    protected RequestContext ctx;
    protected HttpServletRequest request;

    protected RequestContext getRequestContext() {
        return RequestContext.getCurrentContext();
    }

    protected HttpServletRequest getHttpServletRequest() {
        if (ctx == null) {
            ctx = getRequestContext();
        }
        return ctx.getRequest();
    }

    protected void init() {
        ctx = getRequestContext();
        request = getHttpServletRequest();
    }
}
