package org.scoula.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Log4j2
public class CommonExceptionAdvice {

    @ExceptionHandler(
            NoHandlerFoundException.class
    )
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404(
            NoHandlerFoundException ex,
            HttpServletRequest request,
            Model model) {

        log.error(
                "404 오류: {}",
                request.getRequestURI(),
                ex
        );

        model.addAttribute(
                "uri",
                request.getRequestURI()
        );

        return "custom404";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(
            Exception ex,
            Model model) {

        log.error(
                "예외 발생",
                ex
        );

        model.addAttribute(
                "exception",
                ex
        );

        return "error_page";
    }

}