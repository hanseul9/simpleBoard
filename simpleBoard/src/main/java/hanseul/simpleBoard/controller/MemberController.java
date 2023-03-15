package hanseul.simpleBoard.controller;

import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.responsedto.BasicResponse;
import hanseul.simpleBoard.responsedto.member.GetMemberResponseDto;
import hanseul.simpleBoard.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private static final int SUCCESS = 200;
    private static final int CREATED = 201;

    private final MemberService memberService;


    @GetMapping("/member/{memberId}") //회원 단건 조회
    public ResponseEntity<BasicResponse> getMemberById(@PathVariable("memberId") Long memberId) {
        Optional<Member> memberOptional = memberService.findOne(memberId);

        Member member = memberOptional.orElseThrow(() -> new EntityNotFoundException("회원 조회 실패"));

        GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(member);
        BasicResponse basicResponse = new BasicResponse<>("회원 조회 성공", getMemberResponseDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }


}
