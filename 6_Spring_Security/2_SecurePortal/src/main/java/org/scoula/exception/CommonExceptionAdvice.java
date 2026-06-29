package org.scoula.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@ControllerAdvice
public class CommonExceptionAdvice {

    /**
     * 존재하지 않는 URL 요청을 처리합니다.
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404(
            NoHandlerFoundException exception,
            HttpServletRequest request,
            Model model
    ) {
        log.warn(
                "404 요청: {}",
                request.getRequestURI()
        );

        model.addAttribute(
                "uri",
                request.getRequestURI()
        );

        return "custom404";
    }

    /**
     * 처리되지 않은 일반 예외를 처리합니다.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(
            Exception exception,
            Model model
    ) {
        log.error(
                "애플리케이션 오류 발생",
                exception
        );

        model.addAttribute(
                "exception",
                exception
        );

        return "error_page";
    }
}