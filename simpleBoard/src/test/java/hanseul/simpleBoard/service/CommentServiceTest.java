package hanseul.simpleBoard.service;

import hanseul.simpleBoard.domain.Comment;
import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.exception.comment.CommentNotFoundException;
import hanseul.simpleBoard.repository.CommentRepository;
import hanseul.simpleBoard.repository.MemberRepository;
import hanseul.simpleBoard.repository.PostRepository;
import hanseul.simpleBoard.requestdto.comment.CommentCreateRequestDto;
import hanseul.simpleBoard.requestdto.comment.CommentUpdateRequestDto;
import hanseul.simpleBoard.requestdto.member.MemberCreateDto;
import hanseul.simpleBoard.requestdto.post.PostCreateRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class CommentServiceTest {
    @Autowired
    CommentService commentService;
    @Autowired
    MemberService memberService;
    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CommentRepository commentRepository;

    private Long memberId;
    private Long postId;

    @BeforeEach
    public void testBefore() {

        MemberCreateDto memberCreateDto
                = new MemberCreateDto("Test@naver.com", "password", "nickName");
        memberId = memberService.createMember(memberCreateDto);

        PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto("title", "content");
        postId = postService.createPost(memberId, postCreateRequestDto).getId();

    }
    @AfterEach
    public void testAfter() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("댓글 생성 테스트")
    @Transactional
    void 댓글_생성_테스트() {
        // given
        Post post = postService.findOne(postId);
        Member member = memberService.findOne(memberId);
        CommentCreateRequestDto commentDto = new CommentCreateRequestDto("test content");

        // when
        Comment createdComment = commentService.createComment(post, member, commentDto);

        // then
        assertNotNull(createdComment.getId());
        assertEquals(commentDto.getContent(), createdComment.getContent());
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    @Transactional
    void 댓글_삭제_테스트() {
        // given
        Post post = postService.findOne(postId);
        Member member = memberService.findOne(memberId);
        CommentCreateRequestDto commentDto = new CommentCreateRequestDto("test content");
        Comment comment = commentService.createComment(post, member, commentDto);
        Long commentId = comment.getId();

        // when
        commentService.deleteComment(commentId);

        //then
        assertThrows(CommentNotFoundException.class, () -> {
            commentService.findOne(commentId );
        });
    }


    @Test
    @DisplayName("댓글 수정 테스트")
    @Transactional
    void 댓글_수정_테스트() {
        // given
        Post post = postService.findOne(postId);
        Member member = memberService.findOne(memberId);
        CommentCreateRequestDto commentDto = new CommentCreateRequestDto("test content");
        Comment comment = commentService.createComment(post, member, commentDto);


        CommentUpdateRequestDto updateDto = new CommentUpdateRequestDto ("updated content");

        // when
        Comment updatedComment = commentService.updateComment(comment.getId(), updateDto);

        // then
        assertNotNull(updatedComment.getId());
        assertEquals(updateDto.getContent(), updatedComment.getContent());
    }
}


