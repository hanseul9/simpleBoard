package hanseul.simpleBoard.service;

import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.exception.post.PostNotFoundException;
import hanseul.simpleBoard.repository.MemberRepository;
import hanseul.simpleBoard.repository.PostRepository;
import hanseul.simpleBoard.requestdto.member.MemberCreateDto;
import hanseul.simpleBoard.requestdto.post.PostCreateRequestDto;
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
        PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto("title", "content");
        Long memberId = memberService.findByEmail("Test@naver.com").getId();

        //when
        Post post = postService.createPost(memberId, postCreateRequestDto);

        //then
        assertNotNull(post);
        assertEquals(postCreateRequestDto.getTitle(), post.getTitle());
        assertEquals(postCreateRequestDto.getContent(), post.getContent());
    }

    @Test
    public void 페이지_업데이트() {
        //given
        PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto("title", "content");
        Long memberId = memberService.findByEmail("Test@naver.com").getId();
        Post post = postService.createPost(memberId, postCreateRequestDto);

        PostCreateRequestDto postUpdateDto = new PostCreateRequestDto("updateTitle", "updateContent");
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
        PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto("title", "content");
        Long memberId = memberService.findByEmail("Test@naver.com").getId();
        Post post = postService.createPost(memberId, postCreateRequestDto);


        //when
        postService.deletePost(post.getId());

        //then
        assertThrows(PostNotFoundException.class, () -> {
            postService.findOne(post.getId());
        });

    }

}