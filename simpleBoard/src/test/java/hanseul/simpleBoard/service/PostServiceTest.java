package hanseul.simpleBoard.service;

import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.exception.member.MemberNotFoundException;
import hanseul.simpleBoard.exception.post.PostNotFoundException;
import hanseul.simpleBoard.repository.MemberRepository;
import hanseul.simpleBoard.repository.PostRepository;
import hanseul.simpleBoard.requestdto.member.MemberCreateDto;
import hanseul.simpleBoard.requestdto.post.PostRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PostServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void testBefore() {

        MemberCreateDto memberCreateDto
                = new MemberCreateDto("Test@naver.com", "password", "nickName");

        memberService.createMember(memberCreateDto);
    }

    @AfterEach
    public void testAfter() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 페이지_생성() {
        //given
        PostRequestDto postRequestDto = new PostRequestDto("title", "content");
        Long memberId = memberService.findByEmail("Test@naver.com").getId();

        //when
        Long createdPostId = postService.createPost(memberId, postRequestDto);

        //then
        assertNotNull(createdPostId);
    }

    @Test
    public void 페이지_업데이트() {
        //given
        PostRequestDto postRequestDto = new PostRequestDto("title", "content");
        Long memberId = memberService.findByEmail("Test@naver.com").getId();
        Long createdPostId = postService.createPost(memberId, postRequestDto);

        PostRequestDto postUpdateDto = new PostRequestDto("updateTitle", "updateContent");
        //when
        Long updatePostId = postService.updatePost(createdPostId, postUpdateDto);
        Post post = postService.findOne(updatePostId);

        // then
        assertNotNull(updatePostId);
        assertEquals(postUpdateDto.getTitle(), post.getTitle());
        assertEquals(postUpdateDto.getContent(), post.getContent());

    }

    @Test
    public void 페이지_삭제() {
        //given
        PostRequestDto postRequestDto = new PostRequestDto("title", "content");
        Long memberId = memberService.findByEmail("Test@naver.com").getId();
        Long createdPostId = postService.createPost(memberId, postRequestDto);

        //when
        postService.deletePost(createdPostId);

        //then
        assertThrows(PostNotFoundException.class, () -> {
            postService.findOne(createdPostId);
        });

    }

}