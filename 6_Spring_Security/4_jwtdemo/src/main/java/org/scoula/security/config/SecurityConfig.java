package org.scoula.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.scoula.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Map;

// Spring Security 설정 클래스임을 선언
@Configuration
// Spring Security의 웹 보안 기능을 활성화
@EnableWebSecurity
// final 필드를 매개변수로 받는 생성자를 자동 생성
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // DB에서 사용자 정보를 조회하는 서비스
    private final UserDetailsService userDetailsService;

    // 요청의 JWT를 검사하여 인증 정보를 등록하는 필터
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 비밀번호를 BCrypt 방식으로 암호화하고 비교하는 객체 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 로그인 요청에서 사용할 AuthenticationManager를 스프링 빈으로 등록
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()
            throws Exception {

        return super.authenticationManagerBean();
    }

    // 사용자 조회 방법과 비밀번호 비교 방법을 Spring Security에 설정
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth
                // 로그인 시 UserDetailsService를 이용해 DB 사용자 조회
                .userDetailsService(userDetailsService)

                // 입력 비밀번호와 DB의 암호화된 비밀번호를 BCrypt로 비교
                .passwordEncoder(passwordEncoder());
    }

    // URL 접근 권한, 세션, JWT 필터, 인증 실패 처리 등을 설정
    /*
    HttpSecurity는 Spring Security에서 어떤 요청을 허용하고, 어떤 요청을 인증하게 할지 설정     하는 클래스입니다.보안 규칙을 만드는 설정 도구입니다.
    어 로그인 없이 접근 가능한 주소, 로그인해야 접근 가능한 주소, CSRF 사용 여부, JWT 필터 등     록 등을 설정합니다.
    */
    @Override
    protected void configure(HttpSecurity http)
            throws Exception {

        http
                // 브라우저 기본 인증 창을 사용하는 HTTP Basic 인증 비활성화
                .httpBasic()
                .disable()

                // JWT 방식은 세션 기반 CSRF 보호를 사용하지 않으므로 비활성화
                .csrf()
                .disable()

                // Spring Security의 기본 로그인 화면과 폼 로그인 비활성화
                .formLogin()
                .disable()

                // 다른 출처의 클라이언트 요청을 허용하기 위한 CORS 설정 적용
                .cors()
                .configurationSource(corsConfigurationSource())
                //.and()는 앞에서 설정하던 옵션을 마치고, 상위 설정 객체인 HttpSecurity로                      돌아가는 역할을 합니다.
                .and()

                // 세션 관리 방식 설정
                .sessionManagement()

                // 서버에 로그인 세션을 만들지 않는 JWT 무상태 방식 사용
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

                // URL별 접근 권한 설정 시작
                .authorizeRequests()

                //.antMatchers(...).permitAll()은 지정한 URL들은 로그인하지 않은 사용자도                  //접근할 수 있도록 허용한다는 의미입니다.
                .antMatchers(
                        "/",
                        "/auth/login",
                        "/auth/profile",
                        "/api/auth/login",
                        "/resources/**"
                )
                .permitAll()

                // 인증된 사용자만 접근할 수 있는 URL
                .antMatchers("/api/auth/me")
                .authenticated()

                // 위에서 지정하지 않은 나머지 요청은 모두 허용
                .anyRequest()
                .permitAll()

                .and()

                // 인증이나 인가 과정에서 발생하는 예외 처리 설정
                .exceptionHandling()

                // 인증되지 않은 사용자가 인증 필요 URL에 접근했을 때 실행
                .authenticationEntryPoint(
                        (request, response, exception) -> {

                            // HTTP 상태 코드를 401 Unauthorized로 설정
                            response.setStatus(401);

                            // 한글 메시지가 깨지지 않도록 UTF-8 설정
                            response.setCharacterEncoding("UTF-8");

                            // 응답 데이터 형식을 JSON으로 설정
                            response.setContentType(
                                    MediaType.APPLICATION_JSON_VALUE
                            );

                            // 클라이언트에 반환할 인증 실패 응답 데이터
                            Map<String, Object> body = Map.of(
                                    "success", false,
                                    "status", 401,
                                    "message", "인증이 필요한 요청입니다."
                            );

                            // Java 객체를 JSON 문자열로 변환하는 객체
                            ObjectMapper objectMapper =
                                    new ObjectMapper();

                            // 응답 객체를 JSON 문자열로 변환하여 클라이언트에 전송
                            response.getWriter().write(
                                    objectMapper.writeValueAsString(body)
                            );
                        }
                );

        // 아이디·비밀번호 인증 필터보다 먼저 JWT 인증 필터 실행
        http.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );
    }

    // 외부 출처에서 오는 요청을 허용하기 위한 CORS 설정 객체 등록
    @Bean
    public UrlBasedCorsConfigurationSource
    corsConfigurationSource() {

        // CORS 허용 규칙을 저장하는 객체 생성
        CorsConfiguration configuration =
                new CorsConfiguration();

        // 모든 출처의 요청을 허용
        configuration.setAllowedOriginPatterns(
                List.of("*")
        );

        // 허용할 HTTP 요청 방식 설정
        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        // 모든 요청 헤더를 허용
        configuration.setAllowedHeaders(
                List.of("*")
        );

        // 클라이언트 JavaScript가 Authorization 응답 헤더를 읽도록 허용
        configuration.setExposedHeaders(
                List.of("Authorization")
        );

        // 쿠키나 세션 정보를 포함한 요청은 허용하지 않음
        configuration.setAllowCredentials(false);

        // URL별 CORS 설정을 관리하는 객체 생성

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        // 모든 URL에 위의 CORS 설정 적용
        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}