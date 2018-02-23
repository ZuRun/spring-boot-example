package me.zuhr.demo.basis.config;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 解决SpringBoot的中文乱码问题
 *
 * @author zurun
 * @date 2018/2/11 11:28:49
 */
//@Configuration
public class CustomMVCConfiguration extends WebMvcConfigurerAdapter {
//    @Bean
//    public HttpMessageConverter<String> responseBodyConverter() {
//        StringHttpMessageConverter converter = new StringHttpMessageConverter(
//                Charset.forName("UTF-8"));
//        return converter;
//    }
//
//    @Override
//    public void configureMessageConverters(
//            List<HttpMessageConverter<?>> converters) {
//        super.configureMessageConverters(converters);
//        converters.add(responseBodyConverter());
//    }
//
//    @Override
//    public void configureContentNegotiation(
//            ContentNegotiationConfigurer configurer) {
//        configurer.favorPathExtension(false);
//    }
}
