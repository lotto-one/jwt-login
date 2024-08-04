package kr.co.ict.jwtdemo.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    // 사용자의 고유 식별자. 데이터베이스에서 자동으로 생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName; // 이름
    private String lastName; // 성
    private String username; // 아이디
    private String password; // 비밀번호

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String firstName, String lastName, String username, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // 사용자에게 부여된 권한(ADMIN, USER) Role을 읽어 오기 위한 객체
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    // alt + Shift + s
    // 사용자의 아이디를 반환 하는데 , 사용자를 식별할 때 사용되는 메서드
    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    // 계정이 만료 상태를 반환
    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료가 안됨으로 설정
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 안잠김
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 여부(비밀번호) 가 만료 안됨
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정이 활성화가 됨
    }

}