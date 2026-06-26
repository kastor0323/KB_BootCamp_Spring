package org.scoula.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(
            Authentication authentication,
            Model model
    ) {
        log.info("메인 페이지 요청");

        if (authentication != null) {
            model.addAttribute(
                    "loginId",
                    authentication.getName()
            );

            model.addAttribute(
                    "authorities",
                    authentication.getAuthorities()
            );
        }

        return "index";
    }
}