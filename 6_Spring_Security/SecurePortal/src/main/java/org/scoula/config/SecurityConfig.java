package org.scoula.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Log4j2
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@SuppressWarnings("deprecation")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;

    /**
     * BCrypt 비밀번호 암호화 객체입니다.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * DB 사용자 인증에 사용할
     * UserDetailsService와 PasswordEncoder를 등록합니다.
     */
    @Override
    protected void configure(
            AuthenticationManagerBuilder auth
    ) throws Exception {

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * Spring Security Filter Chain 내부에서 사용할
     * UTF-8 필터입니다.
     */
    private CharacterEncodingFilter securityEncodingFilter() {
        CharacterEncodingFilter filter =
                new CharacterEncodingFilter();

        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        return filter;
    }

    /**
     * URL별 접근 권한, 로그인, 로그아웃,
     * 권한 부족 처리를 설정합니다.
     */
    @Override
    protected void configure(
            HttpSecurity http
    ) throws Exception {

        http
                // POST 요청 한글 깨짐 방지
                .addFilterBefore(
                        securityEncodingFilter(),
                        CsrfFilter.class
                )

                // URL 접근 권한 설정
                .authorizeRequests()

                // 로그인하지 않아도 접근 가능
                .antMatchers(
                        "/",
                        "/favicon.ico",
                        "/resources/**",
                        "/auth/login",
                        "/auth/public",
                        "/auth/denied"
                )
                .permitAll()

                // ADMIN 권한 필요
                .antMatchers("/auth/admin")
                .hasRole("ADMIN")

                // MEMBER 또는 ADMIN 권한 필요
                .antMatchers("/auth/member")
                .hasAnyRole("MEMBER", "ADMIN")

                /*
                 * 나머지는 permitAll로 설정합니다.
                 *
                 * 존재하지 않는 URL 요청이 로그인 페이지로
                 * 이동하지 않고 MVC의 404 처리로 전달됩니다.
                 */
                .anyRequest()
                .permitAll()

                .and()

                // 커스텀 로그인
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login-process")
                .usernameParameter("username")
                .passwordParameter("password")

                /*
                 * 사용자가 보호 페이지를 먼저 요청했다면
                 * 로그인 후 원래 요청 페이지로 돌아갑니다.
                 *
                 * 직접 로그인 페이지로 들어왔다면 "/"로 이동합니다.
                 */
                .defaultSuccessUrl("/", false)

                // 로그인 실패
                .failureUrl("/auth/login?error=true")
                .permitAll()

                .and()

                // 로그아웃
                .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/login?logout=true")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies(
                        "JSESSIONID",
                        "remember-me"
                )
                .permitAll()

                .and()

                // 로그인했지만 권한이 없는 경우
                .exceptionHandling()
                .accessDeniedPage("/auth/denied");
    }
}