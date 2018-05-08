package me.zuhr.demo.wxapp.controller;

import me.zuhr.demo.wxapp.api.PasswordManagerRemoteApi;
import me.zuhr.demo.wxapp.base.WxAppBaseController;
import me.zuhr.demo.wxapp.service.PwdManagerService;
import me.zuhr.demo.wxapp.vo.PassWordInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2018/5/9 00:58:34
 */
@RestController
public class PasswordManagerController extends WxAppBaseController implements PasswordManagerRemoteApi {

    @Autowired
    PwdManagerService pwdManagerService;

    @Override
    public void add(PassWordInfoVo passWordInfoVo) {
        String token = getToken();
    }

    @Override
    public PassWordInfoVo get(Long id) {
        return null;
    }
}
