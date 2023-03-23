package hanseul.simpleBoard.controller;

import hanseul.simpleBoard.requestdto.member.MemberLoginDto;
import hanseul.simpleBoard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

//@RestController
@Controller
@RequiredArgsConstructor
public class WebController {

    private final MemberService memberService;

    @GetMapping({"/", "/home"}) //홈페이지
    public String homePage() {
        return "home";
    }

    @GetMapping("/login") //로그인 페이지
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup") // 회원가입 페이지
    public String signupPage() {
        return "signup";
    }

//    @PostMapping("/login-process") //로그인 정보를 받음
//    public String login(MemberLoginDto dto) {
//        boolean isValidMember = memberService.isValidMember(dto.getEmail(), dto.getPassword());
//        if (isValidMember) //로그인 성공시
//            return "login";
//        return "login"; //로그인 실패시
//    }

    @GetMapping("/posts")
    public String getPostTitles() {
        return "posts";
    }

    @GetMapping("/posting")
    public String writePost() {
        return "posting";
    }


}