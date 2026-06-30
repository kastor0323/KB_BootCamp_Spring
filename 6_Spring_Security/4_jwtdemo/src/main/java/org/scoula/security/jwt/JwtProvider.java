package org.scoula.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.scoula.security.account.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/*
`createToken()`은 사용자 정보를 Payload에 담고, Header와 함께 비밀 키로 서명하여 JWT를 생성합니다.
`parseClaims()`는 JWT의 서명을 검증한 뒤 Payload에 들어 있는 Claims를 꺼냅니다.
`validateToken()`은 토큰의 서명, 만료 여부, 형식 오류를 검사하고, `getUsername()`은 Payload의 subject에서 아이디를 반환합니다.
*/

@Component
@Log4j2
public class JwtProvider {

    // JWT 서명에 사용할 비밀 키
    private final Key key;

    // Access Token의 유효 시간
    private final long expiration;

    // 설정 파일의 비밀 키와 유효 시간을 주입받아 초기화
    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration
    ) {
        // Base64 문자열로 저장된 비밀 키를 바이트 배열로 변환
        byte[] keyBytes = Decoders.BASE64.decode(secret);

        // 바이트 배열을 JWT 서명용 HMAC 키로 변환
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.expiration = expiration;
    }

    // 로그인한 사용자 정보를 이용해 JWT를 생성
    public String createToken(CustomUserDetails user) {
        // 토큰 발급 시간
        Date now = new Date();

        // 현재 시간에 유효 시간을 더해 만료 시간 계산
        Date expirationDate = new Date(
                now.getTime() + expiration
        );

        // 사용자 정보와 발급·만료 시간을 담아 JWT 생성
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("memberId", user.getMemberId())
                .claim("name", user.getName())
                .claim("role", user.getRole())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    // JWT를 검증하고 Payload의 Claims를 반환

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT의 서명과 만료 여부를 검사
    /*
    Jwts.parserBuilder()
    JWT를 해석하고 검증할 파서를 만들기 위한 빌더를 생성합니다.
    .setSigningKey(key)
    JWT의 서명이 올바른지 검사할 때 사용할 비밀 키를 설정합니다.
    .build()
    설정한 비밀 키를 기반으로 실제 JWT 파서 객체를 생성합니다.
    .parseClaimsJws(token)
    전달받은 JWT를 해석하면서 서명과 만료 시간을 검증합니다.
   .getBody()
    검증이 완료된 JWT의 Payload 부분인 Claims를 반환합니다.
    */
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;

        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT입니다.");

        } catch (JwtException e) {
            log.warn("유효하지 않은 JWT입니다: {}", e.getMessage());

        } catch (IllegalArgumentException e) {
            log.warn("JWT 값이 비어 있습니다.");
        }

        return false;
    }

    // JWT의 subject에서 사용자 아이디를 반환
    public String getUsername(String token) {
        return parseClaims(token).getSubject();
    }

    // 설정된 JWT 유효 시간을 반환
    public long getExpiration() {
        return expiration;
    }
}