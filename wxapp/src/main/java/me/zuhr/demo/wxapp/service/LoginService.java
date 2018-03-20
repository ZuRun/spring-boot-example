package me.zuhr.demo.wxapp.service;

import me.zuhr.demo.std.BaseService;
import me.zuhr.demo.wxapp.config.WxAppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zurun
 * @date 2018/3/19 12:28:32
 */
@Service
public class LoginService extends BaseService {
    @Autowired
    WxAppConfiguration wxAppConfiguration;

    public String login(String jsCode) {
        String appId = wxAppConfiguration.getAppId();
        String appSecret = wxAppConfiguration.getAppSecret();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={appId}&secret={secret}&js_code={js_code}&grant_type=authorization_code";
        return restTemplate.getForObject(url, String.class, appId, appSecret, jsCode);
    }
}
