package kr.co.ict.jwtdemo.employees;


import org.springframework.web.bind.annotation.GetMapping;

//@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login"; // 뷰 이름
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }

    @GetMapping("/")
    public String defaultIndex() {
        return "index";
    }
}