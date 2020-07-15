package com.mushr00m.config;

import com.mushr00m.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**")
        .excludePathPatterns("**/*login*/**",
                "/**/*register*/**", "/**/*success*/**", "/**/*error*/**");

    }
}
