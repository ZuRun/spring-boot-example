package me.zuhr.demo.ecp.action;

import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.std.BaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 断路器练习
 *
 * @author zurun
 * @date 2018/3/11 12:21:25
 */
@RestController
@RequestMapping("hystrix")
public class HystrixAction extends BaseService {

    @GetMapping("/test1")
    public Result<String> consumer() {
        return Result.ok("调用成功!").addResult("哈哈哈");
    }

}
