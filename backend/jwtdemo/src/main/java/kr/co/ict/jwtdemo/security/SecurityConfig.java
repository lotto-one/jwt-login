package kr.co.ict.jwtdemo.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import kr.co.ict.jwtdemo.jwt.JwtTokenFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // JWT Filter
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // UserDetailsService, PasswordEncoder
    // DaoAuthenticationProvider 지정하는 빈 메서드
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // BCryptPasswordEncoder 비밀번호를 암호화 처리 하는 빈 메서드
    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .cors((cors) -> cors.configurationSource(corsConfigurationSource()))
                .formLogin((login) -> login.disable())
                .httpBasic((basic) -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        // .requestMatchers("/", "/main").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        // .requestMatchers("/api/employees/**").permitAll() // 이경로는 허용
                        // .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated() // 다른 경로는 인증이 필요하다.
                ) // JWT 토큰 필터 추가: JwtTokenFilter를
                  // BasicAuthenticationFilter 전에 추가하여 JWT 토큰을 검증
                .addFilterBefore(jwtTokenFilter, BasicAuthenticationFilter.class)
                // 세션 정책 설정: SessionCreationPolicy.STATELESS로 설정하여 세션 기반 인증을 사용하지 않도록 한다.
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout((logout) -> logout.disable());
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(
                List.of("http://localhost:8081/", "http://192.168.0.42:8081/", "http://192.168.0.42/","http://172.30.1.64"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return urlBasedCorsConfigurationSource;

    }
}
