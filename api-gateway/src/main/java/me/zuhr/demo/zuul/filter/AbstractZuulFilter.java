package me.zuhr.demo.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zurun
 * @date 2018/4/21 22:54:19
 */
public abstract class AbstractZuulFilter extends ZuulFilter {
    @Autowired
    protected BeanFactory beanFactory;

    protected RequestContext getRequestContext() {
        return RequestContext.getCurrentContext();
    }

    @Override
    public boolean shouldFilter() {
//        return false;
        RequestContext ctx = getRequestContext();

        // 如果前一个过滤器的结果为true或者没有设置"sendZuulResponse"，则说明上一个过滤器成功了,需要进入当前的过滤，
        // 如果前一个过滤器的结果为false，则说明上一个过滤器没有成功，则无需进行下面的过滤动作了，直接跳过此过滤器并返回结果
        return ctx.sendZuulResponse();
    }
}
