package hanseul.simpleBoard.controller;

import hanseul.simpleBoard.config.auth.CustomSecurityUtil;
import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.requestdto.member.MemberCreateDto;
import hanseul.simpleBoard.responsedto.BasicResponse;
import hanseul.simpleBoard.responsedto.member.MemberGetResponseDto;
import hanseul.simpleBoard.responsedto.member.MemberJoinResponse;
import hanseul.simpleBoard.responsedto.post.PostListDto;
import hanseul.simpleBoard.service.CommentService;
import hanseul.simpleBoard.service.MemberService;
import hanseul.simpleBoard.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private static final int SUCCESS = 200;
    private static final int CREATED = 201;

    private final MemberService memberService;
    private final PostService postService;
    private final CommentService commentService;
    private final CustomSecurityUtil customSecurityUtil;

    @PostMapping ("/members")//회원가입
    public ResponseEntity<MemberJoinResponse> createMember(@Valid @RequestBody MemberCreateDto memberCreateDto) {

        Long memberId = memberService.createMember(memberCreateDto);
        MemberJoinResponse joinMemberResponse = new MemberJoinResponse(memberId, CREATED, "회원가입 완료");
        return new ResponseEntity<>(joinMemberResponse, HttpStatus.CREATED);
    }

    @GetMapping("/members") //로그인한 회원 조회 api
    public ResponseEntity<BasicResponse> getCurrentUser() {
        Long memberId = customSecurityUtil.getMemberId();
        Member member = memberService.findOne(memberId);
        MemberGetResponseDto getMemberResponseDto = new MemberGetResponseDto(member);
        BasicResponse basicResponse = new BasicResponse<>("회원 조회 성공", getMemberResponseDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }
    @PostMapping("/logout") //로그아웃
    public ResponseEntity<BasicResponse> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = customSecurityUtil.getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        BasicResponse basicResponse = new BasicResponse<>("로그아웃 성공", null);
        return ResponseEntity.ok(basicResponse);
    }


    @GetMapping("/members/{memberId}") //회원 단건 조회
    public ResponseEntity<BasicResponse<MemberGetResponseDto>> getMemberById(@PathVariable("memberId") Long memberId) {
        Member member = memberService.findOne(memberId);
        MemberGetResponseDto getMemberResponseDto = new MemberGetResponseDto(member);
        BasicResponse<MemberGetResponseDto> basicResponse = new BasicResponse<>("회원 조회 성공", getMemberResponseDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @GetMapping("/members/{memberId}/posts") // 특정 회원의 게시글 목록 조회
    @PreAuthorize("@customSecurityUtil.isMemberOwner(#memberId)")
    public ResponseEntity<Page<PostListDto>> getMembersPosts(@PathVariable Long memberId,
                                                             @RequestParam(value = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Post> posts = postService.findByMemberIdOrderByPostedAtDescIdAsc(pageable, memberId);
        List<PostListDto> postListDtos = posts.getContent().stream()
                .map(PostListDto::new)
                .collect(Collectors.toList());
        PageImpl<PostListDto> postList = new PageImpl<>(postListDtos, pageable, posts.getTotalElements());
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    @DeleteMapping("/members/{memberId}/comments/{commentId}")
    @PreAuthorize("@customSecurityUtil.isMemberOwner(#memberId) and @customSecurityUtil.isCommentOwner(#commentId)")
    public ResponseEntity<BasicResponse> deleteMembersComment(@PathVariable Long memberId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        BasicResponse memberBasicResponse = new BasicResponse("댓글 삭제 완료", null);
        return new ResponseEntity<>(memberBasicResponse, HttpStatus.OK);
    }

}
