package com.example.todo_app.security;

import com.example.todo_app.domain.MemberEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "KIMWANGDOL525252DoLKiITty";

    // JWT Token 생성
    public String create(MemberEntity memberEntity) {
        // 기한: 1일
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );

        return Jwts.builder()
                . signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                // payload에 들어갈 내용
                .setSubject(memberEntity.getId())
                .setIssuer("kim sky")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    /*
    parseClaimsJws 메서드가 Base 64로 디코딩 및 파싱
    헤더와 페이로드를 setSigningkey 로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
    위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 날림
    memberId가 필요하므로 getBody 를 부름
     */

    // 토큰 인증
    // 토큰을 디코딩 및 파싱, 토큰의 위조 여부 확인
    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
