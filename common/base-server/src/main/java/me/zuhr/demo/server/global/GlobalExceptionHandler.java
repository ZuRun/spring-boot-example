package me.zuhr.demo.server.global;

import com.alibaba.fastjson.JSONObject;
import me.zuhr.demo.basis.constants.ErrorCode;
import me.zuhr.demo.basis.exception.BusinessException;
import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.server.constants.MyHttpHeader;
import me.zuhr.demo.server.enumration.HttpHeader;
import me.zuhr.demo.server.exception.AbstractRestHttpException;
import me.zuhr.demo.server.exception.AbstractRestServerException;
import me.zuhr.demo.server.service.LoggerService;
import me.zuhr.demo.server.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zurun
 * @date 2018/2/26 11:17:17
 */
@RestControllerAdvice
@Controller
public class GlobalExceptionHandler extends AbstractErrorController {

    /**
     * 默认错误页面路径,可以考虑放在配置文件中
     */
    private static String errorPath = "/error";

    @Autowired
    LoggerService loggerService;

    @Value("${spring.application.name}")
    private String serverName;

    public GlobalExceptionHandler(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    /**
     * 返回404
     *
     * @param request
     * @return
     */
    @RequestMapping
    @ResponseBody
    public ResponseEntity<Result> error(HttpServletRequest request) {
        // TODO-zurun 日志
        HttpHeaders headers = createHeaders(HttpHeader.ExceptionType.NOT_FOUND);
        return new ResponseEntity<>(Result.fail(404, "未找到请求路径！"), headers, HttpStatus.NOT_FOUND);
    }

    /**
     * 返回404页面，接口中content-type为text/html的也返回json
     *
     * @param request
     * @param response
     * @return
     */
//    @RequestMapping(produces = {"text/html"})
//    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
//        HttpStatus status = getStatus(request);
//        response.setStatus(status.value());
//        return new ModelAndView("error");
//    }

    /**
     * rest请求接收到的业务异常,直接继续向调用方抛出
     *
     * @param e
     * @return 用于springBoot框架返回response的body
     * @ResponseStatus 返回的 HTTP 状态码为 HttpStatus.ZDY(自定义状态码 590)
     */
    @ExceptionHandler(AbstractRestServerException.class)
    public ResponseEntity<String> handlerException(AbstractRestServerException e) {
        sendLog(e);

        HttpHeaders headers = createHeaders(e.getExceptionType());
        return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);

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
        return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
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
        json.put("errMsg", e.getMessage());
        json.put("exceptionName", e.getClass().getSimpleName());
        return json;
    }


    @Override
    public String getErrorPath() {
        return null;
    }
}
