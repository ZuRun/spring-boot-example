package me.zuhr.demo.server.global;

import com.alibaba.fastjson.JSONObject;
import me.zuhr.demo.basis.exception.ServiceException;
import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.server.exception.MyRestServerException;
import me.zuhr.demo.server.service.LoggerService;
import me.zuhr.demo.server.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zurun
 * @date 2018/2/26 11:17:17
 */
@RestControllerAdvice
@Component
public class GlobalExceptionHandler {

    @Autowired
    LoggerService loggerService;


    /**
     * 自定义异常
     *
     * @param e
     * @return 用于springBoot框架返回response的body
     * @ResponseStatus 返回的 HTTP 状态码为 HttpStatus.ZDY(自定义状态码 590)
     */
    @ResponseStatus(HttpStatus.ZDY)
    @ExceptionHandler(MyRestServerException.class)
    public Result handlerException(MyRestServerException e) {
        sendLog(e);
        return Result.fail(9, e.getMessage());
    }

    /**
     * 自定义异常
     *
     * @param e
     * @return 用于springBoot框架返回response的body
     * @ResponseStatus 返回的 HTTP 状态码为 HttpStatus.ZDY(自定义状态码 590)
     */
    @ResponseStatus(HttpStatus.ZDY)
    @ExceptionHandler(ServiceException.class)
    public Result handlerException(ServiceException e) {
        sendLog(e);
        return Result.fail(e.getErrCode(), e.getMessage());
    }

    /**
     * 异常,直接返回错误信息
     *
     * @param e
     * @return 用于springBoot框架返回response的body
     * @ResponseStatus 返回的 HTTP 状态码为 HttpStatus.ZDY(自定义状态码 590)
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e) {
        sendLog(e);
        return Result.fail(e.getMessage());
    }


    private void sendLog(ServiceException e) {
        e.printStackTrace();
        JSONObject json = new JSONObject();
        json.put("errCode", e.getErrCode());
        json.put("errMsg", e.getMessage());
        json.put("exceptionName", e.getClass().getName());
        byte[] body = json.toJSONString().getBytes();
        loggerService.sendExceptionLog(body);
    }

    private void sendLog(Exception e) {
        e.printStackTrace();
        JSONObject json = new JSONObject();
        json.put("errCode", 1);
        json.put("errMsg", e.getMessage());
        json.put("exceptionName", e.getClass().getName());
        json.put("stackTrace", ExceptionUtil.getExceptionDetail(e));
        json.put("causeBy", ExceptionUtil.getCauseBy(e));
        byte[] body = json.toJSONString().getBytes();
        loggerService.sendExceptionLog(body);
    }


}
