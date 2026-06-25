package org.scoula.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.JstlView;

/*
 * DispatcherServlet이 사용하는 Servlet WebApplicationContext 설정입니다.
 *
 * 주요 역할:
 *
 * 1. Spring MVC 활성화
 * 2. Controller 스캔
 * 3. 예외 처리 클래스 스캔
 * 4. JSP ViewResolver 설정
 * 5. CSS, JavaScript 등 정적 리소스 설정
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "org.scoula.controller",
        "org.scoula.exception"
})
public class ServletConfig implements WebMvcConfigurer {

    /*
     * 정적 리소스 매핑
     *
     * 브라우저 요청:
     * /resources/css/chat.css
     *
     * 실제 파일:
     * src/main/webapp/resources/css/chat.css
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    /*
     * JSP ViewResolver 설정
     *
     * Controller에서 다음과 같이 반환하면,
     *
     * return "index";
     *
     * 실제 JSP 경로는 다음과 같습니다.
     *
     * /WEB-INF/views/index.jsp
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry
                .jsp("/WEB-INF/views/", ".jsp")
                .viewClass(JstlView.class);
    }
}