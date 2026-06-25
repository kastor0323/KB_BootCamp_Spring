package org.scoula.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * 일반 HTTP 요청을 처리하는 Spring MVC Controller입니다.
 */
@Controller
@Log4j2
public class HomeController {

    /*
     * 프로젝트 루트 주소 요청 처리
     *
     * 요청:
     * GET /
     *
     * 반환:
     * /WEB-INF/views/index.jsp
     */
    @GetMapping("/")
    public String home() {

        log.info("WebSocket 채팅 화면 요청");

        return "index";
    }
}