package me.zuhr.demo.zuul.util;

import com.netflix.zuul.context.RequestContext;
import me.zuhr.demo.basis.model.Result;
import org.springframework.http.MediaType;

/**
 * @author zurun
 * @date 2018/4/30 23:05:11
 */
public class FilterHelper {

    public static void fail(RequestContext ctx , Result result){
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(401);
        ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        ctx.setResponseBody(result.toString());
    }
}
