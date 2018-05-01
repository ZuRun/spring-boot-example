package me.zuhr.demo.zuul.filter.service;

import com.google.common.base.Strings;
import com.netflix.zuul.context.RequestContext;
import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.zuul.constants.ErrorCode;
import me.zuhr.demo.zuul.enumration.ConstantEnum;
import me.zuhr.demo.zuul.util.FilterHelper;
import me.zuhr.demo.zuul.util.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zurun
 * @date 2018/4/30 22:17:32
 */
@Component
public class WxApp implements ServiceFilter {
    @Autowired
    RedisHelper redisHelper;

    @Override
    public boolean authentication(RequestContext ctx) {
        String requestURI = (String) ctx.get("requestURI");
        // 登录请求则放行
        if (ConstantEnum.LOGIN.getValue().equalsIgnoreCase(requestURI)) {
            return true;
        }
        // 非登录请求,全部鉴权
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader("wxapp-token");
        if (Strings.isNullOrEmpty(token)) {
            // 请求头中缺少token
            FilterHelper.fail(ctx, Result.fail(ErrorCode.WxAppErrorCode.TOKEN_MISSING));
            return false;
        }
        // 检查token是否有效
        if (!redisHelper.wxappCheckToken(token)) {
            FilterHelper.fail(ctx, Result.fail(ErrorCode.WxAppErrorCode.TOKEN_INCORRECT));
            return false;
        }
        return true;
    }
}
