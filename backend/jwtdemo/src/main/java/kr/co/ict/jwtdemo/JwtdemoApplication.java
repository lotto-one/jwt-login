package kr.co.ict.jwtdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.co.ict.jwtdemo.employees.Employee;
import kr.co.ict.jwtdemo.employees.EmployeeRepository;
import kr.co.ict.jwtdemo.security.Role;
import kr.co.ict.jwtdemo.security.User;
import kr.co.ict.jwtdemo.security.UserRepository;

//CommandLineRunner 구현해서 run() 재정의 한 후에 빈이 모두 생성된 이후에
//제어할 수 있다.
@SpringBootApplication
public class JwtdemoApplication implements CommandLineRunner {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(JwtdemoApplication.class, args);
	}

	/*
	 * public Employee(String name, String phone, String email, String position) {
	 * this.name = name;
	 * this.phone = phone;
	 * this.email = email;
	 * this.position = position;
	 * }
	 */
	@Override
	public void run(String... args) throws Exception {
		// Employee 테이블에 데이터 하나 등록 해보기
		employeeRepository.save(new Employee("tess", "010-8888-8888", "javabook@naver.com", "TEACHER01"));

		// _user테이블 -> 인증/허가 시큐리티 테이블 *****
		/*
		 * private String firstName; // 이름
		 * private String lastName; // 성
		 * private String username; // 아이디
		 * private String password; // 비밀번호
		 * private Role role;
		 */
		// 비밀번호 인코딩
		String encodedPassword = passwordEncoder.encode("tess01");
		// 관리자
		userRepository.save(new User("tess", "hong", "xman@gmail.com",
				encodedPassword, Role.ADMIN));
		// 일반 사용자
		userRepository.save(new User("tess2", "hon2", "xman2@gmail.com",
				encodedPassword, Role.USER));
	}

}
