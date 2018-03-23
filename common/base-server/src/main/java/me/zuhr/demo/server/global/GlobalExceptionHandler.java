package me.zuhr.demo.server.global;

import com.alibaba.fastjson.JSONObject;
import me.zuhr.demo.basis.exception.BusinessException;
import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.server.constants.MyHttpHeader;
import me.zuhr.demo.server.enumration.HttpHeader;
import me.zuhr.demo.server.exception.AbstractRestHttpException;
import me.zuhr.demo.server.exception.RestException;
import me.zuhr.demo.server.service.LoggerService;
import me.zuhr.demo.server.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zurun
 * @date 2018/2/26 11:17:17
 */
@RestControllerAdvice
@Component
public class GlobalExceptionHandler extends AbstractErrorController {

    /**
     * 默认错误页面路径,可以考虑放在配置文件中
     */
    private static String errorPath = "/error";

    @Autowired
    LoggerService loggerService;

    public GlobalExceptionHandler(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }


    /**
     * rest请求接收到的业务异常,直接继续向调用方抛出
     *
     * @param e
     * @return 用于springBoot框架返回response的body
     * @ResponseStatus 返回的 HTTP 状态码为 HttpStatus.ZDY(自定义状态码 590)
     */
    @ExceptionHandler(RestException.class)
    public ResponseEntity<String> handlerException(RestException e) {
        sendLog(e);

        HttpHeaders headers = createHeaders(e.getExceptionType());
        return new ResponseEntity(e.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /**
     * 接受的respone没有Exception-Type请求头
     *
     * @param e
     * @return
     */
    public ResponseEntity<AbstractRestHttpException> handlerException(AbstractRestHttpException e){
        sendLog(e);
        HttpHeaders headers = createHeaders(HttpHeader.ExceptionType.UNKNOWN);
        return new ResponseEntity(e.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * 自定义业务异常
     *
     * @param e
     * @return 用于springBoot框架返回response的body
     * @ResponseStatus 返回的 HTTP 状态码为 HttpStatus.ZDY(自定义状态码 590)
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result> handlerException(BusinessException e) {
        sendLog(e);
        HttpHeaders headers = createHeaders(HttpHeader.ExceptionType.BUSINESS);

        return new ResponseEntity(Result.fail(e.getErrCode(), e.getMessage()), headers, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /**
     * 异常,直接返回错误信息
     *
     * @param e
     * @return 用于springBoot框架返回response的body
     * @ResponseStatus 返回的 HTTP 状态码为 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> handlerException(Exception e) {
        sendLog(e);
        HttpHeaders headers = createHeaders(HttpHeader.ExceptionType.Exception);
        return new ResponseEntity(Result.fail(e.getMessage()), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 生成header
     *
     * @param httpHeader
     * @return
     */
    private HttpHeaders createHeaders(MyHttpHeader httpHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add(httpHeader.getHeaderName(), httpHeader.getHeaderValue());

        return headers;
    }

    private void sendLog(BusinessException e) {
        e.printStackTrace();
        JSONObject json = new JSONObject();
        json.put("errCode", e.getErrCode());
        json.put("errMsg", e.getMessage());
        json.put("exceptionName", e.getClass().getSimpleName());
        byte[] body = json.toJSONString().getBytes();
        loggerService.sendExceptionLog(body);
    }

    private void sendLog(Exception e) {
        e.printStackTrace();
        JSONObject json = new JSONObject();
        json.put("errCode", 1);
        json.put("errMsg", e.getMessage());
        json.put("exceptionName", e.getClass().getSimpleName());
        json.put("stackTrace", ExceptionUtil.getExceptionDetail(e));
        json.put("causeBy", ExceptionUtil.getCauseBy(e));
        byte[] body = json.toJSONString().getBytes();
        loggerService.sendExceptionLog(body);
    }


    @Override
    public String getErrorPath() {
        return null;
    }
}
