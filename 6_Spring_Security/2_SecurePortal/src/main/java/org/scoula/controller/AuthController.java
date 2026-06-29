package org.scoula.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/auth")
public class AuthController {

    /**
     * 커스텀 로그인 화면입니다.
     *
     * 로그인 POST 처리는 Controller가 아니라
     * UsernamePasswordAuthenticationFilter가 처리합니다.
     */
    @GetMapping("/login")
    public String login(
            Authentication authentication
    ) {
        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {

            return "redirect:/";
        }

        return "auth/login";
    }

    /**
     * 누구나 접근 가능한 공개 페이지입니다.
     */
    @GetMapping("/public")
    public String publicPage() {
        log.info("공개 페이지 접근");

        return "auth/public";
    }

    /**
     * MEMBER 또는 ADMIN 권한이 필요합니다.
     */
    @GetMapping("/member")
    public String memberPage(
            Authentication authentication,
            Model model
    ) {
        log.info(
                "회원 페이지 접근: {}",
                authentication.getName()
        );

        model.addAttribute(
                "username",
                authentication.getName()
        );

        model.addAttribute(
                "authorities",
                authentication.getAuthorities()
        );

        return "auth/member";
    }

    /**
     * ADMIN 권한이 필요합니다.
     */
    @GetMapping("/admin")
    public String adminPage(
            Authentication authentication,
            Model model
    ) {
        log.info(
                "관리자 페이지 접근: {}",
                authentication.getName()
        );

        model.addAttribute(
                "username",
                authentication.getName()
        );

        model.addAttribute(
                "authorities",
                authentication.getAuthorities()
        );

        return "auth/admin";
    }

    /**
     * 로그인했지만 권한이 부족한 경우 표시합니다.
     */
    @GetMapping("/denied")
    public String denied(
            Authentication authentication,
            Model model
    ) {
        if (authentication != null) {
            model.addAttribute(
                    "username",
                    authentication.getName()
            );

            model.addAttribute(
                    "authorities",
                    authentication.getAuthorities()
            );
        }

        return "auth/denied";
    }
}