package me.zuhr.demo.wxapp.config;

import me.zuhr.demo.std.BaseService;

import javax.annotation.PostConstruct;

/**
 * @author zurun
 * @date 2018/4/29 19:07:36
 */
public class WxService extends BaseService{

    /**
     * 微信小程序中,增加对 content type为[text/plain]的处理
     */
    @PostConstruct
    public void after(){
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
    }

}
