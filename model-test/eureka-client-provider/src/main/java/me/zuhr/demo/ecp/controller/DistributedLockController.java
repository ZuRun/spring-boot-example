package me.zuhr.demo.ecp.controller;

import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.redisson.annotation.DistributedLock;
import me.zuhr.demo.std.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2018/4/2 17:57:22
 */
@RestController
public class DistributedLockController extends BaseController {

    @DistributedLock(waitTime = -1L)
    @RequestMapping("/distributedLockTest")
    public Result distributedLockTest() throws InterruptedException {
        logger.info("------distributedLockTest");
        Thread.sleep(3000L);
        logger.info("------"+System.currentTimeMillis());

        return Result.ok();

    }
}
