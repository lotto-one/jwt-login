package kr.co.ict.jwtdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// UserDetailsService 인터페이스를 구현한 클래스
// 이클래스를 사용해서 사용자의 정보를 로딩하는 서비스를 구현 
@Configuration
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            System.out.println("Optional<User> =>" + userRepository.findByUsername(username));
            return userRepository.findByUsername(username).orElseThrow(() -> new Exception("사용자가 없음!"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
