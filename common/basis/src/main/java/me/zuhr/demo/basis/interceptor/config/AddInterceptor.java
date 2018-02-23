package me.zuhr.demo.basis.interceptor.config;

import me.zuhr.demo.basis.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 重写WebMvcConfigurerAdapter中的addInterceptors方法把自定义的拦截器类添加进来即可
 *
 * @author zurun
 * @date 2017/12/27 17:05:57
 */
@Configuration
public class AddInterceptor extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").
                excludePathPatterns("/index.html","/getLogo");
        super.addInterceptors(registry);
    }
}
