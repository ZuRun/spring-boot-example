package me.zuhr.demo.ecp.controller;

import me.zuhr.demo.basis.enumration.ServiceNameEnum;
import me.zuhr.demo.basis.exception.BusinessException;
import me.zuhr.demo.ecp.constants.ErrorCode;
import me.zuhr.demo.server.restful.MyRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2018/3/21 01:51:34
 */
@RestController
public class ExceptionController {
    @Autowired
    MyRestTemplate restTemplate;

    @RequestMapping("/exception")
    public String exception() throws Exception {
        throw new Exception("普通异常!Exception");
    }

    @RequestMapping("/runtimeException")
    public String runtimeException() {
        throw new BusinessException(ErrorCode.ecp.ECP_DEF_ERROR);
//        throw new RuntimeException("运行异常!runtimeException");
    }

    @RequestMapping("/businessException")
    public String businessException() {
        throw new BusinessException("业务运行异常!BusinessException");
    }

    @RequestMapping("/restBusinessException")
    public String restBusinessException() {
        return restTemplate.getForObject("http://" + ServiceNameEnum.ECC.getValue() + "/exception", String.class);
//        throw new RestBusinessException("rest接受业务异常!RestBusinessException");
    }

    @RequestMapping(value = "/notfound", method = RequestMethod.GET)
    public String notfound(@RequestParam("param") String param) {
        return "参数:" + param;
    }
}
