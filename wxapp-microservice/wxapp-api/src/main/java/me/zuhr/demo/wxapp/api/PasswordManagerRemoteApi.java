package me.zuhr.demo.wxapp.api;

import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.wxapp.vo.PassWordInfoVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zurun
 * @date 2018/5/9 00:54:23
 */
@FeignClient("wxapp")
@RequestMapping("pwd")
public interface PasswordManagerRemoteApi {

    /**
     * 新增并返回主键
     *
     * @param passWordInfoVo
     * @return
     */
    @RequestMapping(value = "add", method = {RequestMethod.POST})
    Result<Integer> add(@RequestBody PassWordInfoVo passWordInfoVo);

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    Result<PassWordInfoVo> get(@PathVariable Long id);

    @RequestMapping(value = "simpleList", method = RequestMethod.GET)
    Result<List> getSimpleList();
}
