package me.zuhr.demo.wxapp.base;


import me.zuhr.demo.std.BaseController;

/**
 * @author zurun
 * @date 2018/5/9 01:03:43
 */
public class WxAppBaseController extends BaseController {

    /**
     * 获取token,不需要判null,zuul过滤器中会拦截的
     *
     * @return
     */
    protected String getToken() {
        return request.getHeader("wxapp-token");
    }
}
