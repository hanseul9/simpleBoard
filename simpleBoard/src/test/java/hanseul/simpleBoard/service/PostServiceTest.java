package hanseul.simpleBoard.service;

import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.exception.member.MemberNotFoundException;
import hanseul.simpleBoard.exception.post.PostNotFoundException;
import hanseul.simpleBoard.repository.MemberRepository;
import hanseul.simpleBoard.repository.PostRepository;
import hanseul.simpleBoard.requestdto.member.MemberCreateDto;
import hanseul.simpleBoard.requestdto.post.PostRequestDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

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
        Post post = postService.createPost(memberId, postRequestDto);

        //then
        assertNotNull(post);
        assertEquals(postRequestDto.getTitle(), post.getTitle());
        assertEquals(postRequestDto.getContent(), post.getContent());
    }

    @Test
    public void 페이지_업데이트() {
        //given
        PostRequestDto postRequestDto = new PostRequestDto("title", "content");
        Long memberId = memberService.findByEmail("Test@naver.com").getId();
        Post post = postService.createPost(memberId, postRequestDto);

        PostRequestDto postUpdateDto = new PostRequestDto("updateTitle", "updateContent");
        //when
        Post updatePost = postService.updatePost(post.getId(), postUpdateDto);


        // then
        assertNotNull(updatePost.getId());
        assertEquals(postUpdateDto.getTitle(), updatePost.getTitle());
        assertEquals(postUpdateDto.getContent(), updatePost.getContent());

    }

    @Test
    public void 페이지_삭제() {
        //given
        PostRequestDto postRequestDto = new PostRequestDto("title", "content");
        Long memberId = memberService.findByEmail("Test@naver.com").getId();
        Post post = postService.createPost(memberId, postRequestDto);


        //when
        postService.deletePost(post.getId());

        //then
        assertThrows(PostNotFoundException.class, () -> {
            postService.findOne(post.getId());
        });

    }

}