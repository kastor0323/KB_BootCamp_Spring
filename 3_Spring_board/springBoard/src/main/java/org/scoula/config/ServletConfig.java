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
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc

// Controller와 예외 처리 클래스를 검색합니다.
@ComponentScan(basePackages = {
        "org.scoula.controller",
        "org.scoula.exception"
})
public class ServletConfig
        implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(
            ResourceHandlerRegistry registry) {

        // CSS, JavaScript, 이미지 등의 정적 파일을 처리합니다.
        registry
                .addResourceHandler(
                        "/resources/**"
                )
                .addResourceLocations(
                        "/resources/"
                );
    }

    @Override
    public void configureViewResolvers(
            ViewResolverRegistry registry) {

        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();

        resolver.setViewClass(JstlView.class);

        // JSP 앞 경로
        resolver.setPrefix(
                "/WEB-INF/views/"
        );

        // JSP 확장자
        resolver.setSuffix(".jsp");

        registry.viewResolver(resolver);
    }
/*
파일이 포함된 multipart/form-data 요청이 들어오면
Servlet 3.0의 표준 업로드 기능을 이용해 요청을 분석하고,
업로드된 파일을 MultipartFile 객체로 변환해서
Controller에 전달하도록 Spring Bean을 등록합니다.

@Bean은 개발자가 직접 메서드를 호출해서 사용하는 것이 아닙니다. Spring MVC가 실행될 때 자동으로 등록하고, 파일 업로드 요청이 들어오면 DispatcherServlet이 자동으로 찾아서 사용합니다.
*/

    @Bean
    public MultipartResolver multipartResolver() {

        // Servlet 3.0 표준 파일 업로드 기능을 사용합니다.
        return new StandardServletMultipartResolver();
    }

}