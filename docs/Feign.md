## 注意
- @EnableFeignClients注解不支持继承,需直接加在启动项上

## 错误状态码处理(异常)
参考 `MyResponseErrorHandler`
根据状态码和请求头中的ExceptionType来响应处理

- 相关代码
    - [FeignConfiguration](../common/base-server/src/main/java/me/zuhr/demo/server/config/FeignConfiguration.java)
    - [RestErrorDecoder](../common/base-server/src/main/java/me/zuhr/demo/server/feign/RestErrorDecoder.java)

## 坑
https://my.oschina.net/u/2350117/blog/1603159