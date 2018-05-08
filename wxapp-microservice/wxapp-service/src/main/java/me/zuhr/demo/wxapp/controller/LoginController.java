package me.zuhr.demo.wxapp.controller;

import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.wxapp.api.LoginRemoteApi;
import me.zuhr.demo.wxapp.base.WxAppBaseController;
import me.zuhr.demo.wxapp.service.LoginService;
import me.zuhr.demo.wxapp.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2018/3/19 12:26:54
 */
@RestController
public class LoginController extends WxAppBaseController implements LoginRemoteApi {

    @Autowired
    LoginService loginService;


    @Override
    public Result login(String code) {
        return loginService.login(code);
    }

    @Override
    public Result updateUserInfo(UserInfoVo userInfoVo) {
        String token = getToken();
        loginService.updateUserInfo(token, userInfoVo);
        return Result.ok("更新成功!");
    }
}
