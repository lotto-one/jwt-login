package kr.co.ict.jwtdemo.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

// extends OncePerRequestFilter 상속을 받아야 하고 추상 클래스의 추상메서드
// HTTP 요청마다 doFilterInternal 메소드가 호출되어서 필터 처리를 한다.
// JWT 토큰을 검증하고 인증을 수행
@Component
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    // jwt토큰을 생성하는 클래스
    private JwtTokenProvider jwtTokenProvider;
    // 사용자의 정보를 불러오는데 사용
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        // JWT 토큰 추출
        String token = jwtTokenProvider.resolveToken(request);
        // 정상적인 토큰이라면 - 토큰이 존재해야하고(시큐리티에서 인증을 바다야 하고, 검증까지 끝낸 토큰 )
        if (token != null && jwtTokenProvider.validateToken(token)) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenProvider.getUsername(token));
            // 사용자의 인증정보를 포함한 토큰 객체로 반환
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            // 생성된 인증 객체를 SecurityContextHolder에 설정하여,
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 위에ㅔ서 인증한 사용자 토큰 객체를 스프링시큐리티로 인증으로 저장한다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 다음 필터로 진행
        filterChain.doFilter(request, response);
    }

}
