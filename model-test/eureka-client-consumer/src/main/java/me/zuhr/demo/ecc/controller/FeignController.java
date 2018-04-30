package me.zuhr.demo.ecc.controller;

import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.ecp.feign.FeignServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecp-feign")
public class FeignController {

    @Autowired
    FeignServiceFeignClient feignService;

    @RequestMapping(value = "test1")
    public Result test1(@RequestParam String param){
        return feignService.test1(param);
    }
}
