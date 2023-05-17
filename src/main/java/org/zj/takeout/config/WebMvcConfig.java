package org.zj.takeout.config;

import ch.qos.logback.classic.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.zj.takeout.common.JacksonObjectMapper;

import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     *设置静态资源映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/templates/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/templates/front/");
    }

    /**
     * 扩展消息springmvc消息转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
//        设置对象转换器，底层使用Jackson将java对象转换为Json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
//        将创建的消息转换器添加到mvc框架的转换器集合中
        converters.add(0,messageConverter);
    }
}
