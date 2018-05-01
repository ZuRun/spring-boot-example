package me.zuhr.demo.wxapp.controller;

import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.wxapp.service.LoginService;
import me.zuhr.demo.wxapp.vo.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zurun
 * @date 2018/3/19 12:26:54
 */
@RestController
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    LoginService loginService;


    @RequestMapping(value = "login")
    public Result login(@RequestParam String code) {
        return loginService.login(code);
    }

    @PostMapping(value = "userInfo")
    public Result getUserInfo(HttpServletRequest httpRequest, @RequestBody UserInfo userInfo) {
        String token = httpRequest.getHeader("wxapp-token");

        return Result.ok();
    }
}
