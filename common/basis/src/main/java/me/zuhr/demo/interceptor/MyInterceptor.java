package me.zuhr.demo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zurun
 * @date 2017/12/27 16:59:59
 */
public class MyInterceptor implements HandlerInterceptor {
    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(MyInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        LOGGER.debug("拦截器拦截！");
//        LOGGER.info("拦截器拦截！");
//        LOGGER.error("拦截器拦截！");
//        LOGGER.info(httpServletRequest.getRequestURI());
//        if("/".equals(httpServletRequest.getRequestURI())){
//            httpServletResponse.sendRedirect("404.html");
//
//            return false;
//        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
