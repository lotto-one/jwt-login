package kr.co.ict.jwtdemo.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@AllArgsConstructor: 모든 필드를 인자로 받는 생성자를 자동으로 생성
@AllArgsConstructor
// @NoArgsConstructor: 인자가 없는 기본 생성자를 자동으로 생성
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
    // 사용자 이름을 저장
    private String username;
    // 사용자 비밀번호를 저장
    private String password;
}