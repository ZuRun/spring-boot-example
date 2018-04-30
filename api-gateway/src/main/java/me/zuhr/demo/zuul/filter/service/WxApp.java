package me.zuhr.demo.zuul.filter.service;

import com.netflix.zuul.context.RequestContext;
import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.zuul.enumration.ConstantEnum;
import me.zuhr.demo.zuul.util.FilterHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zurun
 * @date 2018/4/30 22:17:32
 */
public class WxApp implements ServiceFilter {

    @Override
    public boolean authentication(RequestContext ctx) {
       String requestURI= (String) ctx.get("requestURI");
       // 登录请求则放行
       if(ConstantEnum.LOGIN.getValue().equalsIgnoreCase(requestURI)){
            return true;
       }
       // 非登录请求,全部鉴权
        HttpServletRequest request=ctx.getRequest();
        String token = request.getHeader("wxapp-token");

        if(token==null){
            FilterHelper.fail(ctx, Result.fail("鉴权失败"));
        }
        return true;

    }
}
