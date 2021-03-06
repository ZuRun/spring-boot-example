package me.zuhr.demo.ecp.feign;

import me.zuhr.demo.ecp.api.FeignRemoteApi;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "ecp")
public interface FeignServiceFeignClient extends FeignRemoteApi {
}
