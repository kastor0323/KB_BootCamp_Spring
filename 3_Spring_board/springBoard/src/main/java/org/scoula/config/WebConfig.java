package org.scoula.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

public class WebConfig
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    // 업로드 중 사용할 임시 폴더입니다.
    private static final String LOCATION =
            "C:/upload";

    // 파일 한 개의 최대 크기: 10MB
    private static final long MAX_FILE_SIZE =
            1024L * 1024L * 10L;

    // 전체 요청 최대 크기: 50MB
    private static final long MAX_REQUEST_SIZE =
            1024L * 1024L * 50L;

    // 5MB를 넘으면 임시 디스크 파일로 처리합니다.
    private static final int FILE_SIZE_THRESHOLD =
            1024 * 1024 * 5;

    @Override
    protected Class<?>[] getRootConfigClasses() {

        return new Class[]{
                RootConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {

        return new Class[]{
                ServletConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {

        // 모든 요청을 DispatcherServlet이 처리합니다.
        return new String[]{
                "/"
        };
    }

    @Override
    protected Filter[] getServletFilters() {

        CharacterEncodingFilter filter =
                new CharacterEncodingFilter();

        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        return new Filter[]{
                filter
        };
    }

    @Override
    protected void customizeRegistration(
            ServletRegistration.Dynamic registration) {

        // 존재하지 않는 주소를 예외로 처리합니다.
        registration.setInitParameter(
                "throwExceptionIfNoHandlerFound",
                "true"
        );

        MultipartConfigElement multipartConfig =
                new MultipartConfigElement(
                        LOCATION,
                        MAX_FILE_SIZE,
                        MAX_REQUEST_SIZE,
                        FILE_SIZE_THRESHOLD
                );

        registration.setMultipartConfig(
                multipartConfig
        );
    }

}