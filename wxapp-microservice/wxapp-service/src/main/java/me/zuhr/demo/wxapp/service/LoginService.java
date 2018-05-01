package me.zuhr.demo.wxapp.service;

import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.wxapp.config.WxAppConfiguration;
import me.zuhr.demo.wxapp.config.WxService;
import me.zuhr.demo.wxapp.utils.WxappUtils;
import me.zuhr.demo.wxapp.vo.SessionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zurun
 * @date 2018/3/19 12:28:32
 */
@Service
public class LoginService extends WxService {
    @Autowired
    WxAppConfiguration wxAppConfiguration;

    @Autowired
    WxappUtils wxappUtils;

    public Result login(String jsCode) {
        String appId = wxAppConfiguration.getAppId();
        String appSecret = wxAppConfiguration.getAppSecret();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={appId}&secret={secret}&js_code={js_code}&grant_type=authorization_code";
        SessionVo sessionVo=  restTemplate.getForObject(url, SessionVo.class, appId, appSecret, jsCode);
        if(sessionVo.success()){
            return Result.ok().addResult(wxappUtils.login(sessionVo));
        }
        return Result.fail(sessionVo.getErrcode(),sessionVo.getErrmsg());
    }

//    public UserInfo getUserInfo(){
//        UserInfo userInfo;
//        userInfo
//    }
}
