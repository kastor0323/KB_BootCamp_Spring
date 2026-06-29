package org.scoula.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "org.scoula.controller",
        "org.scoula.exception"
})
public class ServletConfig implements WebMvcConfigurer {

    /**
     * 정적 리소스 경로를 등록합니다.
     */
    @Override
    public void addResourceHandlers(
            ResourceHandlerRegistry registry
    ) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    /**
     * JSP ViewResolver를 설정합니다.
     */
    @Override
    public void configureViewResolvers(
            ViewResolverRegistry registry
    ) {
        registry
                .jsp(
                        "/WEB-INF/views/",
                        ".jsp"
                )
                .viewClass(JstlView.class);
    }

    /**
     * 파일 업로드 Resolver를 등록합니다.
     */
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}