package me.zuhr.demo.user.api;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author zurun
 * @date 2018/5/15 15:38:06
 */
@FeignClient("user-server")
public interface UserRemoteApi {
}
