package me.zuhr.demo.zuul.filter.pre;

import com.netflix.zuul.context.RequestContext;
import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.zuul.configuration.JwtConfiguration;
import me.zuhr.demo.zuul.enumration.FilterTypeEnum;
import me.zuhr.demo.zuul.util.FilterHelper;
import me.zuhr.demo.zuul.util.JwtHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
            FilterHelper.fail(ctx, Result.fail("鉴权失败"));
            ctx.addZuulResponseHeader("authorization", JwtHelper.createToken(jwt, new HashMap()));
            logger.info("checkLoginState");
            return null;
        }


        return null;
    }
}
