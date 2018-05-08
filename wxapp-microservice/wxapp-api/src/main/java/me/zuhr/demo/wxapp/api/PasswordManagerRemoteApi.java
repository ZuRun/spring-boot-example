package me.zuhr.demo.wxapp.api;

import me.zuhr.demo.wxapp.vo.PassWordInfoVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author zurun
 * @date 2018/5/9 00:54:23
 */
@FeignClient("wxapp")
@RequestMapping("pwd")
public interface PasswordManagerRemoteApi {

    @PostMapping("add")
    void add(@RequestBody PassWordInfoVo passWordInfoVo);

    @GetMapping("{id}")
    PassWordInfoVo get(@PathVariable Long id);

}
