package hanseul.simpleBoard.controller;

import hanseul.simpleBoard.config.auth.CustomSecurityUtil;
import hanseul.simpleBoard.config.auth.CustomUserDetails;
import hanseul.simpleBoard.requestdto.member.MemberLoginDto;
import hanseul.simpleBoard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;


@Controller
@RequiredArgsConstructor
public class WebController {

    private final MemberService memberService;

    private final CustomSecurityUtil customSecurityUtil;

    private static final Logger logger = LoggerFactory.getLogger(WebController.class);



    @GetMapping({"/", "/home","/login"}) //로그인 페이지
    public String loginPage() {

        Authentication authentication = customSecurityUtil.getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            logger.trace("인증되지 않는 사용자의 접근");
        } else {
            return "posts";
        }

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

    @GetMapping("/myRoom") // 마이룸
    public String myRoom() {
        return "myRoom";
    }

}