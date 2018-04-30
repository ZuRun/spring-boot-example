package me.zuhr.demo.zuul.filter.pre;

import com.netflix.zuul.context.RequestContext;
import me.zuhr.demo.zuul.enumration.FilterTypeEnum;
import me.zuhr.demo.zuul.filter.AbstractZuulFilter;
import me.zuhr.demo.zuul.filter.CheckPermission;

/**
 * @author zurun
 * @date 2018/4/30 17:12:52
 */
public class PermissionFilter extends AbstractZuulFilter {

    @Override
    public String filterType() {
        return FilterTypeEnum.PRE.getValue();
    }

    @Override
    public int filterOrder() {
        return 9;
    }

    @Override
    public Object run() {
        RequestContext ctx = getRequestContext();

        String proxy = (String) ctx.get("proxy");
        // 微服务
        String serviceId = (String) ctx.get("serviceId");
        CheckPermission bean = beanFactory.getBean("", CheckPermission.class);
        return null;
    }
}
