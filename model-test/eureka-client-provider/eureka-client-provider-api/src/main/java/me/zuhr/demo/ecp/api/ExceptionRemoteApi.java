package me.zuhr.demo.ecp.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/exceptin")
public interface ExceptionRemoteApi {
    @RequestMapping("/exception")
    public String exception() throws Exception;

    @RequestMapping("/runtimeException")
    public String runtimeException();

    @RequestMapping("/businessException")
    public String businessException();

    @RequestMapping("/restBusinessException")
    public String restBusinessException();

    @RequestMapping(value = "/notfound", method = RequestMethod.GET)
    public String notfound(@RequestParam("param") String param);
}
