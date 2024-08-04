package kr.co.ict.jwtdemo.jwt;

// 모든 필드를 인자로 받는 생성자를 자동으로 생성
import lombok.AllArgsConstructor;
import lombok.Getter;
// 인자 없이 객체를 생성할 수 있는 기본 생성자를 자동으로 생성
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    // 사용자 인증 후 생성된 접근 토큰을 저장
    private String accessToken;
}