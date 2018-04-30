package me.zuhr.demo.ecp.feign;

import me.zuhr.demo.ecp.api.ExceptionRemoteApi;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author ZuRun
 * @date 2018/04/12 23:32:04
 */
@FeignClient(name = "ecp")
public interface ExceptionFeignClient extends ExceptionRemoteApi{
}
