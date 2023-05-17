package hanseul.simpleBoard.controller;

import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.requestdto.member.MemberCreateDto;
import hanseul.simpleBoard.responsedto.BasicResponse;
import hanseul.simpleBoard.responsedto.member.GetMemberResponseDto;
import hanseul.simpleBoard.responsedto.member.JoinMemberResponse;
import hanseul.simpleBoard.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private static final int SUCCESS = 200;
    private static final int CREATED = 201;

    private final MemberService memberService;

    @PostMapping ("/member")//회원가입
    public ResponseEntity<JoinMemberResponse> createMember(@Valid @RequestBody MemberCreateDto memberCreateDto) {

        Long memberId = memberService.createMember(memberCreateDto);

        JoinMemberResponse joinMemberResponse = new JoinMemberResponse(memberId, CREATED, "회원가입 완료");
        return new ResponseEntity<>(joinMemberResponse, HttpStatus.CREATED);
    }

    @GetMapping("/member") //로그인한 회원 조회 api
    public ResponseEntity<BasicResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberService.findByEmail(email);
        GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(member);
        BasicResponse basicResponse = new BasicResponse<>("회원 조회 성공", getMemberResponseDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }
    @PostMapping("/logout") //로그아웃
    public ResponseEntity<BasicResponse> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        BasicResponse basicResponse = new BasicResponse<>("로그아웃 성공", null);
        return ResponseEntity.ok(basicResponse);
    }


    @GetMapping("/member/{memberId}") //회원 단건 조회
    public ResponseEntity<BasicResponse> getMemberById(@PathVariable("memberId") Long memberId) {
        Member member = memberService.findOne(memberId);
        GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(member);
        BasicResponse basicResponse = new BasicResponse<>("회원 조회 성공", getMemberResponseDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }


}
