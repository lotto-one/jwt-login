package kr.co.ict.jwtdemo.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys; //JWT
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

//2 
@Component
// 로깅을 위한 Logger를 자동으로 생성
@Slf4j
public class JwtTokenProvider {
    // Key key: JWT 생성 및 검증에 사용되는 비밀 키, HS512 알고리즘으로 생성
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // Authentication :
    // // Authentication : 시큐리티에서 인증 상태를 나타내는 객체
    // 시큐리티에 의해서 인증된 사용자에 대해서만 JWT을 생성하는 메서드
    public String createToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000);
        /*
         * Jwts.builder()
         * .setter
         * .compact(); //=> aaaa.bbbbb.ccccc 형식의 HS256 알고리즘의 토큰이 발생한다.
         */
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                // .signWith(SignatureAlgorithm.HS512,key) 였다.
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 제공된 JWT 토큰이 유효한지 검증하기 위해서 만든다.
    public boolean validateToken(String token) {
        // Check if the token is valid and not expired
        try {
            // Jwts.parser().setSigningKey(key).parseClaimsJws(token); 라인을 사용하여 토큰의 유효성을 검사
            // Jwts.parser().setSigningKey(key).parseClaimsJws(token); => deprecated
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            // 토큰값이 유효하면 true
            return true;
        } catch (MalformedJwtException ex) { // 토큰의 형식이 올바르지 않을 때 발생
            log.error("Invalid JWT token : 토큰의 형식이 올바르지 않음 ");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token : 토큰이 만료되었을 때 발생");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token : 지원되지 않는 토큰 유형일 때 발생");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty : 토큰이 비어있거나 null일 때 발생");
        } catch (SignatureException e) {
            log.error("there is an error with the signature of you token : 토큰의 서명이 유효하지 않을 때 발생");
        }
        // 토큰이 유효하지 않으면 false
        return false;
    }

    public String getUsername(String token) {
        // Extract the username from the JWT token
        // return Jwts.parser()
        // .setSigningKey(key)
        // .parseClaimsJws(token)
        // .getBody()
        // .getSubject();
        //// Jwts.parser().setSigningKey(key).parseClaimsJws(token)가
        // Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token) 로 변경
        // 새로운 방식으로 키 설정 및 토큰 파싱
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
