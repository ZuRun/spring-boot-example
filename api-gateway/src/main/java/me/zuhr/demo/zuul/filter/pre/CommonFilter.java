package me.zuhr.demo.zuul.filter.pre;

import com.netflix.zuul.context.RequestContext;
import me.zuhr.demo.zuul.enumration.FilterTypeEnum;
import me.zuhr.demo.zuul.filter.Mapping;

/**
 * @author zurun
 * @date 2018/4/30 17:12:52
 */
public class CommonFilter extends AbstractZuulFilter {

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

        // 根据需要访问的微服务名,进行相应的过滤操作
        Mapping.getByName(serviceId).getCheck().authentication(ctx);


        return null;
    }
}
