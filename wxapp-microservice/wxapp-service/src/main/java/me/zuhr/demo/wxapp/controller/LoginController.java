package me.zuhr.demo.wxapp.controller;

import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.std.BaseController;
import me.zuhr.demo.wxapp.api.LoginRemoteApi;
import me.zuhr.demo.wxapp.service.LoginService;
import me.zuhr.demo.wxapp.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zurun
 * @date 2018/3/19 12:26:54
 */
@RestController
public class LoginController extends BaseController implements LoginRemoteApi  {

    @Autowired
    LoginService loginService;


    @Override
    public Result login(String code) {
        return loginService.login(code);
    }

    @Override
    public Result updateUserInfo(HttpServletRequest httpRequest, UserInfoVo userInfoVo) {
        String token = httpRequest.getHeader("wxapp-token");
        loginService.updateUserInfo(token, userInfoVo);
        return Result.ok("更新成功!");
    }
}
