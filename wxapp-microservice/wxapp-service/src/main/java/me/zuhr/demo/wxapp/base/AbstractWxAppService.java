package me.zuhr.demo.wxapp.base;


import me.zuhr.demo.std.BaseService;
import me.zuhr.demo.wxapp.constants.ErrorCode;
import me.zuhr.demo.wxapp.exception.WxAppException;
import me.zuhr.demo.wxapp.utils.WxappUtils;
import me.zuhr.demo.wxapp.vo.SessionVo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zurun
 * @date 2018/5/9 01:03:43
 */
public class AbstractWxAppService extends BaseService {
    @Autowired
    WxappUtils wxappUtils;

    /**
     * 获取token,不需要判null,zuul过滤器中会拦截的
     *
     * @return
     */
    protected String getToken() {
        return request.getHeader("wxapp-token");
    }

    /**
     * 获取sessionVo
     *
     * @return
     */
    protected SessionVo getSessionVo() {
        SessionVo sessionVo = wxappUtils.getSession(getToken());
        if (sessionVo == null || sessionVo.getOpenid() == null) {
            throw new WxAppException(ErrorCode.WxAppErrorCode.INVALID_TOKEN);
        }
        return sessionVo;
    }
}
