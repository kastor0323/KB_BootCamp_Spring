package org.scoula.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordEncoderTests {

    @Test
    void encodePassword() {
        PasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String encodedPassword =
                encoder.encode("1234");

        System.out.println(
                "암호화된 비밀번호: "
                        + encodedPassword
        );

        assertTrue(
                encoder.matches(
                        "1234",
                        encodedPassword
                )
        );
    }
}