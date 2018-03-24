# 微服务中 通信 与 异常 的处理

## 项目中的异常处理

建议微服务中不要try catch,在网关层处理下AbstractRestHttpException和RestUnhandledException异常就好了,因为此异常信息可能对用户不友好

### ![异常继承关系,密码:boot](http://on-img.com/chart_image/5ab60ee8e4b007d2511816c7.png)

### 异常通用处理
- 项目中抛的异常信息会一路向上抛到最初调用方服务,对于调用方来说,无法感知到接受到的异常信息是被调用服务还是被调用服务请求的其他服务抛的异常信息
- 被调用方抛的异常,会在response的请求头中加入Exception-Type请求头
- 调用方接到异常会抛出AbstractRestServerException异常,调用方可根据实际情况捕获

### 业务异常
- 义务异常中会记录ErrorCode错误码,在抛出业务异常的时候可以传入,也可以忽略,直接抛错误信息
- 模块中如果想抛出自定义的错误码,需要加入自定义错误码枚举,可以参考[ECC模块](../model-test/eureka-client-consumer/src/main/java/me/zuhr/demo/ecc/constants/ErrorCode.java)中写的

### 未特殊处理的异常
- 调用方将接收到的response的Exception-Type请求头会为EXCEPTION,

### 无Exception-Type请求头
 一把这种情况为调用第三方接口
- 调用方会抛出AbstractRestHttpException异常,并在全局异常处理中返回的response中加入值为UNKOWN的Exception-Type请求头, message为返回的response的body内容

## [错误码定义](../common/basis/src/main/java/me/zuhr/demo/basis/constants/ErrorCode.java)
### 1位
0 成功
1 默认错误码
2 无效的错误码

### 通用异常 - 2位

### http状态码为异常码 - 3位 
### 业务异常 - 4位 
- 首位，应用标识； 1
- 二号位  ，应用模块标识（common：0 | wxapp : 1 | ecc : 8 | ecp : 9）；
- 三四位为错误码
