package me.zuhr.demo.server.global;

import me.zuhr.demo.server.exception.MyRestServerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zurun
 * @date 2018/2/26 11:17:17
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 自定义异常,直接返回错误字符串
     *
     * @param e
     * @return 用于springBoot框架返回response的body
     * @ResponseStatus 返回的 HTTP 状态码为 HttpStatus.ZDY(自定义状态码 590)
     */
    @ResponseStatus(HttpStatus.ZDY)
    @ExceptionHandler(MyRestServerException.class)
    public String handlerException(MyRestServerException e) {
        return e.getMessage();
    }
}
