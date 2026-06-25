package org.scoula.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration;

/*
 * web.xml을 대신하는 Java 기반 웹 애플리케이션 초기화 클래스입니다.
 *
 * 주요 역할:
 *
 * 1. Root WebApplicationContext 생성
 * 2. Servlet WebApplicationContext 생성
 * 3. DispatcherServlet 등록
 * 4. URL "/" 매핑
 * 5. UTF-8 인코딩 필터 등록
 * 6. 404 예외 발생 설정
 */
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    /*
     * Root WebApplicationContext 설정
     *
     * Service, Repository, DataSource 등을 관리하는
     * 부모 ApplicationContext입니다.
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                RootConfig.class
        };
    }

    /*
     * Servlet WebApplicationContext 설정
     *
     * ServletConfig:
     * - Spring MVC
     * - Controller
     * - JSP
     * - 정적 리소스
     *
     * WebSocketConfig:
     * - WebSocket 엔드포인트
     * - STOMP 메시지 브로커
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
                ServletConfig.class,
                WebSocketConfig.class
        };
    }

    /*
     * DispatcherServlet URL 매핑
     *
     * "/"로 설정하면 모든 웹 요청을
     * DispatcherServlet이 처리합니다.
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{
                "/"
        };
    }

    /*
     * UTF-8 한글 인코딩 필터
     */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter filter =
                new CharacterEncodingFilter();

        filter.setEncoding("UTF-8");

        /*
         * 요청과 응답 모두 UTF-8을 강제로 적용합니다.
         */
        filter.setForceEncoding(true);

        return new Filter[]{
                filter
        };
    }

    /*
     * DispatcherServlet 추가 설정
     */
    @Override
    protected void customizeRegistration(
            ServletRegistration.Dynamic registration) {

        /*
         * 존재하지 않는 주소를 요청했을 때
         * NoHandlerFoundException이 발생하도록 설정합니다.
         *
         * CommonExceptionAdvice에서 이 예외를 받아
         * custom404.jsp로 이동합니다.
         */
        registration.setInitParameter(
                "throwExceptionIfNoHandlerFound",
                "true"
        );
    }
}