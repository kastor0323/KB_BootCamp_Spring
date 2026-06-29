package org.scoula.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

public class WebConfig
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final String UPLOAD_LOCATION =
            "C:/upload/securePortal";

    private static final long MAX_FILE_SIZE =
            10L * 1024L * 1024L;

    private static final long MAX_REQUEST_SIZE =
            50L * 1024L * 1024L;

    private static final int FILE_SIZE_THRESHOLD =
            5 * 1024 * 1024;

    /**
     * Service, Mapper, DataSource, Security를 관리하는
     * Root WebApplicationContext 설정입니다.
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                RootConfig.class,
                SecurityConfig.class
        };
    }

    /**
     * Controller, ViewResolver를 관리하는
     * Servlet WebApplicationContext 설정입니다.
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
                ServletConfig.class
        };
    }

    /**
     * DispatcherServlet을 "/"에 매핑합니다.
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{
                "/"
        };
    }

    /**
     * 일반 MVC 요청의 한글 인코딩 필터입니다.
     */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter =
                new CharacterEncodingFilter();

        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);

        return new Filter[]{
                encodingFilter
        };
    }

    /**
     * DispatcherServlet의 추가 옵션을 설정합니다.
     */
    @Override
    protected void customizeRegistration(
            ServletRegistration.Dynamic registration
    ) {
        registration.setInitParameter(
                "throwExceptionIfNoHandlerFound",
                "true"
        );

        MultipartConfigElement multipartConfig =
                new MultipartConfigElement(
                        UPLOAD_LOCATION,
                        MAX_FILE_SIZE,
                        MAX_REQUEST_SIZE,
                        FILE_SIZE_THRESHOLD
                );

        registration.setMultipartConfig(multipartConfig);
    }
}