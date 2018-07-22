package me.zuhr.demo.server.global;

import com.alibaba.fastjson.JSONObject;
import me.zuhr.demo.basis.constants.ErrorCode;
import me.zuhr.demo.basis.exception.BusinessException;
import me.zuhr.demo.basis.exception.MyRuntimeException;
import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.server.constants.MyHttpHeader;
import me.zuhr.demo.server.enumration.HttpHeader;
import me.zuhr.demo.server.exception.AbstractRestHttpException;
import me.zuhr.demo.server.exception.AbstractRestServerException;
import me.zuhr.demo.server.service.LoggerService;
import me.zuhr.demo.server.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;

/**
 * 全局异常处理
 * 不同类型的异常具体返回的http状态码,根据业务来调整
 * 对外服务建议返回的http状态码统一为200,系统内部调用的返回500就好了
 *
 * @author zurun
 * @date 2018/2/26 11:17:17
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    LoggerService loggerService;

    @Value("${spring.application.name}")
    private String serverName;

    /**
     * rest请求接收到的业务异常,直接继续向调用方抛出
     *
     * @param e
     * @return 用于springBoot框架返回response的body
     */
    @ExceptionHandler(AbstractRestServerException.class)
    public ResponseEntity<String> handlerException(AbstractRestServerException e) {
        sendLog(e);

        HttpHeaders headers = createHeaders(e.getExceptionType());
        // todo-zurun 此处返回的状态码 也许应该用500?
        return new ResponseEntity<>(e.getMessage(), headers, e.getStatusCode());

    }

    /**
     * 接受的response没有Exception-Type请求头
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AbstractRestHttpException.class)
    public ResponseEntity<String> handlerException(AbstractRestHttpException e) {
        sendLog(e);
        HttpHeaders headers = createHeaders(HttpHeader.ExceptionType.UNKNOWN);
        // todo-zurun 此处返回的状态码 也许应该用500?
        return new ResponseEntity<>(e.getMessage(), headers, e.getStatusCode());
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

        return new ResponseEntity<>(Result.fail(e.getErrCode(), e.getMessage()), headers, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /**
     * 自定义非业务运行异常
     *
     * @param e
     * @return 用于springBoot框架返回response的body
     * @ResponseStatus 返回的 HTTP 状态码为 HttpStatus.ZDY(自定义状态码 590)
     */
    @ExceptionHandler(MyRuntimeException.class)
    public ResponseEntity<Result> handlerException(MyRuntimeException e) {
        sendLog(e);
        HttpHeaders headers = createHeaders(HttpHeader.ExceptionType.Z_RUNTIME);

        return new ResponseEntity<>(Result.fail(e.getErrCode(), e.getMessage()), headers, HttpStatus.INTERNAL_SERVER_ERROR);

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
        HttpHeaders headers = createHeaders(HttpHeader.ExceptionType.UNHANDLED);
        return new ResponseEntity<>(Result.fail(ErrorCode.common.DEFAULT_EXCEPTION_CODE.getErrCode(), e.getMessage()), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 404页面
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Result> handlerException(NoHandlerFoundException e) {
        sendLog(e);
        HttpHeaders headers = createHeaders(HttpHeader.ExceptionType.NOT_FOUND);
        return new ResponseEntity<>(Result.fail(404, "未找到请求路径！"), headers, HttpStatus.NOT_FOUND);
    }

    /**
     * 请求method错误(post、get)
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Result> handlerException(HttpRequestMethodNotSupportedException e) {
        sendLog(e);
        HttpHeaders headers = createHeaders(HttpHeader.ExceptionType.NOT_FOUND);
        return new ResponseEntity<>(Result.fail(404, "未找到请求路径！").addResult(e.getMessage()), headers, HttpStatus.NOT_FOUND);
    }

    /**
     * 请求错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<Result> handlerException(ServletException e) {
        sendLog(e);
        HttpHeaders headers = createHeaders(HttpHeader.ExceptionType.SERVLET_EXCEPTION);
        return new ResponseEntity<>(Result.fail(400, "请求错误！").addResult(e.getMessage()), headers, HttpStatus.BAD_REQUEST);
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

    // 记录日志

    /**
     * 服务抛的业务异常
     *
     * @param e
     */
    private void sendLog(BusinessException e) {
        e.printStackTrace();
        JSONObject json = createJsonObject(e);
        json.put("errCode", e.getErrCode());
        byte[] body = json.toJSONString().getBytes();
        loggerService.sendExceptionLog(body);
    }

    /**
     * rest接收到的服务端抛的异常
     *
     * @param e
     */
    private void sendLog(AbstractRestServerException e) {
        e.printStackTrace();
        JSONObject json = createJsonObject(e);
        json.put("errCode", e.getStatusCode().value());
        byte[] body = json.toJSONString().getBytes();
        loggerService.sendExceptionLog(body);
    }

    /**
     * 请求头中没有Exception-Type请求头
     *
     * @param e
     */
    private void sendLog(AbstractRestHttpException e) {
        e.printStackTrace();
        JSONObject json = createJsonObject(e);
        json.put("errCode", e.getStatusCode().value());
        json.put("stackTrace", ExceptionUtil.getExceptionDetail(e));
        json.put("causeBy", ExceptionUtil.getCauseBy(e));
        byte[] body = json.toJSONString().getBytes();
        loggerService.sendExceptionLog(body);
    }

    /**
     * 请求错误
     *
     * @param e
     */
    private void sendLog(ServletException e) {
        e.printStackTrace();
        JSONObject json = createJsonObject(e);
        json.put("errCode", 400);
        json.put("stackTrace", ExceptionUtil.getExceptionDetail(e));
        json.put("causeBy", ExceptionUtil.getCauseBy(e));
        byte[] body = json.toJSONString().getBytes();
        loggerService.sendExceptionLog(body);
    }

    private void sendLog(Exception e) {
        e.printStackTrace();
        JSONObject json = createJsonObject(e);
        json.put("errCode", -1);
        json.put("stackTrace", ExceptionUtil.getExceptionDetail(e));
        json.put("causeBy", ExceptionUtil.getCauseBy(e));
        byte[] body = json.toJSONString().getBytes();
        loggerService.sendExceptionLog(body);
    }

    private JSONObject createJsonObject(Exception e) {
        JSONObject json = new JSONObject();
        json.put("serverName", serverName);
        json.put("time", System.currentTimeMillis());
        json.put("errMsg", e.getMessage());
        json.put("exceptionName", e.getClass().getSimpleName());
        return json;
    }

}
