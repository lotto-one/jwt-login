package kr.co.ict.jwtdemo.jwt;

//1 
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//BasicAuthenticationEntryPoint 가 하는 목적은 
// Http로 접속되는 jWt 인증처리 와같은 기본 인증처리가 실패 처리를 구현한 클래스 
@Component
public class JwtAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    // Bean의 속성들이 설정이 다 끝난 후에 호출!
    @Override
    public void afterPropertiesSet() {
        // JWT 실행한다
        setRealmName("JwT Authentication");
        super.afterPropertiesSet();
    }

    // AuthenticationException : 인증 실패와 관련된 예외 정보이다.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        // HTTP 응답 상태를 401 (Unauthorized)로 설정 해놓고 인증되지 않은 요청값으로 상태값을 응답으로 설정한다.
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 마임타입을 json으로 응답 시켜놓는다.
        response.setContentType("application/json");
        // 에러 메세지를 설정
        // PrintWriter out= response.getWriter();
        response.getWriter().write("{ \"message\": \"" + authException.getMessage() + "\" }");
    }

}
