package me.zuhr.demo.zuul.filter.pre;

import com.netflix.zuul.context.RequestContext;
import me.zuhr.demo.zuul.enumration.FilterTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

/**
 * 检查是否登录
 *
 * @author zurun
 * @date 2018/4/21 20:19:48
 */
public class CheckLoginStateFilter extends AbstractZuulFilter {
    private static Logger logger = LoggerFactory.getLogger(CheckLoginStateFilter.class);

    @Override
    public String filterType() {
        return FilterTypeEnum.PRE.getValue();
    }

    @Override
    public int filterOrder() {
        return 11;
    }

    @Override
    public Object run() {
        RequestContext ctx = getRequestContext();

        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(401);
        ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        ctx.setResponseBody("{\"code\":2}");
//        request.getHeader()
        logger.info("checkLoginState");
        return null;
    }
}
