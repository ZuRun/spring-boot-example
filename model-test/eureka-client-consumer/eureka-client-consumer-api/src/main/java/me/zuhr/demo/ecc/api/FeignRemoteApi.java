package me.zuhr.demo.ecc.api;

import me.zuhr.demo.basis.model.Result;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/feign")
public interface FeignRemoteApi {

    @RequestMapping(value = "test1")
    Result test1(String param);
}
