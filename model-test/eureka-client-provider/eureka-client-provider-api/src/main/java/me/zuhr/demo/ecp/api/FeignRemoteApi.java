package me.zuhr.demo.ecp.api;

import me.zuhr.demo.basis.model.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/feign")
public interface FeignRemoteApi {

    @RequestMapping(value = "test1")
    Result test1(@RequestParam(value = "param") String param);


}
