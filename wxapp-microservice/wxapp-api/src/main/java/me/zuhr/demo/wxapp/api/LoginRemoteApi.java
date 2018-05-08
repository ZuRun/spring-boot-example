package me.zuhr.demo.wxapp.api;

import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.wxapp.vo.UserInfoVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zurun
 * @date 2018/5/8 23:38:34
 */
public interface LoginRemoteApi {

    @RequestMapping(value = "login")
    Result login(@RequestParam String code);

    @PostMapping(value = "userInfo")
    Result updateUserInfo(HttpServletRequest httpRequest, @RequestBody UserInfoVo userInfoVo);
}
