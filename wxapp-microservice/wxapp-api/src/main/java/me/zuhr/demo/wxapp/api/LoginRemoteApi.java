package me.zuhr.demo.wxapp.api;

import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.wxapp.vo.UserInfoVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zurun
 * @date 2018/5/8 23:38:34
 */
@FeignClient("wxapp")
public interface LoginRemoteApi {

    @RequestMapping(value = "login")
    Result login(@RequestParam String code);

    @PostMapping(value = "userInfo")
    Result updateUserInfo(@RequestBody UserInfoVo userInfoVo);
}
