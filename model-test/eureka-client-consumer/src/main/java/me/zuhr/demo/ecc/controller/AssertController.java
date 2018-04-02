package me.zuhr.demo.ecc.controller;

import me.zuhr.demo.basis.model.Result;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2018/3/21 23:24:31
 */
@RestController
public class AssertController {

    @RequestMapping("/assert")
    public Result assertTest() {
        Assert.isTrue(false, "true");
        return Result.ok();
    }
}
