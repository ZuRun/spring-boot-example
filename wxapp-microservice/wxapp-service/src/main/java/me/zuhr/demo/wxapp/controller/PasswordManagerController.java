package me.zuhr.demo.wxapp.controller;

import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.wxapp.api.PasswordManagerRemoteApi;
import me.zuhr.demo.wxapp.base.AbstractWxAppController;
import me.zuhr.demo.wxapp.service.PwdManagerService;
import me.zuhr.demo.wxapp.vo.PassWordInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2018/5/9 00:58:34
 */
@RestController
public class PasswordManagerController extends AbstractWxAppController implements PasswordManagerRemoteApi {

    @Autowired
    PwdManagerService pwdManagerService;

    @Override
    public Result<Integer> add(@RequestBody PassWordInfoVo passWordInfoVo) {
        return Result.ok().addResult(pwdManagerService.add(passWordInfoVo));
    }

    @Override
    public Result<PassWordInfoVo> get(Long id) {
//        pwdManagerService.
        return null;
    }
}
