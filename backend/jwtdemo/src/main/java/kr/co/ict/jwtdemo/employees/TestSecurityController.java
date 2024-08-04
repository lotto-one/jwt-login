package kr.co.ict.jwtdemo.employees;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


//http://192.168.0.42/jwtdemo/api/other/test 
//접근 거부가 출력이 됨 
@Controller
@RequestMapping("/api/other")
public class TestSecurityController {
    @GetMapping("/test")
    public String message(Model model) {
        System.out.println("/api/test 호출이 됨 ");
        // Authentication : 시큐리티에서 인증 상태를 나타내는 객체
        // SecurityContextHolder :
        // SecurityContextHolder.getContext().getAuthentication()
        // 스프링 시큐리티에서 인증된 사용자의 정보를 가지고 있는 Authentication 객체를
        // 반환 받을 수 있다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        String role = null;
        // 인증을 했냐
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
            // userDetails.getAuthorities() 컬랙션에서 for문 사용하지 않고 role의 정보를 추출
            role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst().orElse(null);
        }
        model.addAttribute("username", username);
        model.addAttribute("role", role);
        return "other";
    }
}
