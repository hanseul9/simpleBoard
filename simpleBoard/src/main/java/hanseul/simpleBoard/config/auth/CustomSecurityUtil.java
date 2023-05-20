package hanseul.simpleBoard.config.auth;

import hanseul.simpleBoard.domain.Comment;
import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.repository.MemberRepository;
import hanseul.simpleBoard.service.CommentService;
import hanseul.simpleBoard.service.MemberService;
import hanseul.simpleBoard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomSecurityUtil{

    /**
     * 권한 제어 전에 해당 객체가 먼저 존재하는건지 확인하기
     */

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PostService postService;
    private final CommentService commentService;


    // 현재 인증된 사용자의 정보를 가져오는 메소드
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getMemberId() {
        Authentication authentication = getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getId();
        } else if (principal instanceof DefaultOAuth2User) {
            // 소셜 로그인을 통해 반환된 사용자 정보를 바탕으로 회원 ID를 조회
            String email = (String) ((DefaultOAuth2User) principal).getAttributes().get("email");
            Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                    new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
            return member.getId();
        } else {
            throw new IllegalArgumentException("알 수 없는 사용자 타입입니다.");
        }
    }


    public boolean isMemberOwner(Long memberId) { //memberId를 가지고 본인소유인지 파악

        memberService.findOne(memberId); //회원 먼저 확인

        Authentication authentication = getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            Long currentUserId = userDetails.getId();
            return currentUserId.equals(memberId); // 현재 사용자의 ID와 요청한 ID가 일치하는지 확인
        }
        return false; // 인증 정보가 없는 경우 false 반환
    }

    public boolean isPostOwner(Long postId) {
        Long memberId = getMemberId();
        Post post = postService.findOne(postId);


        if (memberId.equals(post.getMember().getId())) {
            return true;
        }
        else{
            throw new AccessDeniedException("해당 포스트에 대한 접근 권한이 없습니다.");
        }
    }

    public boolean isCommentOwner(Long commentId) {
        Long memberId = getMemberId();
        Comment comment = commentService.findOne(commentId);

        if (memberId.equals(comment.getMember().getId())) {
            return true;
        }
        else{
            throw new AccessDeniedException("해당 댓글 대한 접근 권한이 없습니다.");
        }
    }


}