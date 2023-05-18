package hanseul.simpleBoard.controller;

import hanseul.simpleBoard.requestdto.member.MemberLoginDto;
import hanseul.simpleBoard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;


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


    @GetMapping("/posts") // 글 목록들
    public String getPostTitles() {
        return "posts";
    }

    @GetMapping("/postDetail") // 게시글 열람
    public String getPost() {
        return "postDetail";
    }

    @GetMapping("/posting") //글쓰기 페이지
    public String writePost() {
        return "posting";
    }


}