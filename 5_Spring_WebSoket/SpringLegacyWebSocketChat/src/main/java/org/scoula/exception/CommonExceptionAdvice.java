package org.scoula.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/*
 * 일반 Spring MVC 요청에서 발생하는 예외를 공통 처리합니다.
 *
 * 주의:
 * WebSocket 메시지 처리 중 발생한 예외는
 * 일반 MVC 예외 처리와 처리 방식이 다릅니다.
 *
 * 이 클래스는 주로 HTTP 페이지 요청에서 발생하는
 * 일반 예외와 404 오류를 처리합니다.
 */
@ControllerAdvice
@Log4j2
public class CommonExceptionAdvice {

    /*
     * 존재하지 않는 주소를 요청했을 때 처리합니다.
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404(
            NoHandlerFoundException exception,
            HttpServletRequest request,
            Model model) {

        String requestUri = request.getRequestURI();

        log.warn(
                "404 오류 발생: {}",
                requestUri,
                exception
        );

        /*
         * custom404.jsp에서 요청 주소를 출력합니다.
         */
        model.addAttribute(
                "uri",
                requestUri
        );

        return "custom404";
    }

    /*
     * 일반 예외 처리
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(
            Exception exception,
            Model model) {

        log.error(
                "일반 예외 발생",
                exception
        );

        /*
         * error_page.jsp에서 오류 정보를 출력할 수 있도록
         * Model에 예외 객체를 저장합니다.
         */
        model.addAttribute(
                "exception",
                exception
        );

        return "error_page";
    }
}