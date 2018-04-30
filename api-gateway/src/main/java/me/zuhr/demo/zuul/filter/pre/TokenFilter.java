package me.zuhr.demo.zuul.filter.pre;

import com.netflix.zuul.context.RequestContext;
import me.zuhr.demo.zuul.configuration.JwtConfiguration;
import me.zuhr.demo.zuul.enumration.FilterTypeEnum;
import me.zuhr.demo.zuul.filter.AbstractZuulFilter;
import me.zuhr.demo.zuul.util.JwtHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.HashMap;

/**
 * @author zurun
 * @date 2018/4/24 22:51:10
 */
public class TokenFilter extends AbstractZuulFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JwtConfiguration jwt;

    @Override
    public String filterType() {
        return FilterTypeEnum.PRE.getValue();
    }

    @Override
    public int filterOrder() {
        return 10;
    }


    @Override
    public Object run() {
        RequestContext ctx = getRequestContext();

        String authHeader = ctx.getRequest().getHeader("authorization");

        if (StringUtils.isEmpty(authHeader)) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            ctx.setResponseBody("{\"code\":1}");
            ctx.addZuulResponseHeader("authorization", JwtHelper.createToken(jwt, new HashMap()));
            logger.info("checkLoginState");
            return null;
        }


        return null;
    }
}
