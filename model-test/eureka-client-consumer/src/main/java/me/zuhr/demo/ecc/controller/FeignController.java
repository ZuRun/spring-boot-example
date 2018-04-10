package me.zuhr.demo.ecc.controller;

import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.ecc.api.FeignRemoteApi;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController implements FeignRemoteApi {
    @Override
    public Result test1(String param) {
        return Result.fail("错误");
    }
}
