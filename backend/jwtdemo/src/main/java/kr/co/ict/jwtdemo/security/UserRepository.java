package kr.co.ict.jwtdemo.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 결과가 없는 경우 Optional.empty()를 반환
    // 결과가 있는 경우 Optional.of(결과)를 반환
    Optional<User> findByUsername(String username);
}
