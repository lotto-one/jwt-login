package kr.co.ict.jwtdemo.employees;

import org.springframework.context.annotation.Bean;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/main").permitAll()
                        .requestMatchers("/api/employees/**").permitAll() // 이경로는 허용
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated() // 다른 경로는 인증이 필요하다.
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 시 호출할 Mapping
                        .logoutSuccessUrl("/login?logout") // 로그아웃 이후 이동할 리다이렉트
                        .permitAll());
        return http.build();
    }
}
