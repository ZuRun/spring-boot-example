package me.zuhr.demo.server.interceptor.config;

import me.zuhr.demo.server.interceptor.LoggerInterceptor;
import me.zuhr.demo.server.interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
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

    /**
     * 这个地方用bean注解是因为:
     * 拦截器执行在自动bean初始化之前.
     * 否则拦截器中bean无法注入
     *
     * @return
     * @see <a href="https://my.oschina.net/u/1790105/blog/1490098">SpringBoot拦截器中Bean无法注入</a>
     */
    @Bean
    public LoggerInterceptor getLoggerInterceptor() {
        return new LoggerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截

        // 记录请求日志
        registry.addInterceptor(getLoggerInterceptor()).addPathPatterns("/**").
                excludePathPatterns("/index.html", "/getLogo");

        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").
                excludePathPatterns("/index.html", "/getLogo");


        // 继续其他拦截器
        super.addInterceptors(registry);
    }
}
