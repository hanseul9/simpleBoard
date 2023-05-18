package hanseul.simpleBoard.controller;

import hanseul.simpleBoard.config.auth.CustomSecurityUtil;
import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.requestdto.post.PostRequestDto;
import hanseul.simpleBoard.responsedto.BasicResponse;
import hanseul.simpleBoard.responsedto.post.PostDto;
import hanseul.simpleBoard.responsedto.post.PostListDto;
import hanseul.simpleBoard.responsedto.post.PostGetDto;
import hanseul.simpleBoard.service.MemberService;
import hanseul.simpleBoard.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private static final int SUCCESS = 200;
    private static final int CREATED = 201;

    private final PostService postService;
    private final MemberService memberService;
    private final CustomSecurityUtil customSecurityUtil;

    @GetMapping("/posts")
    public ResponseEntity<Page<PostListDto>> getPostTitles(@RequestParam(value = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Post> posts = postService.findAllByOrderByPostedAtDesc(pageable);
        List<PostListDto> postListDtos = posts.getContent().stream()
                .map(PostListDto::new)
                .collect(Collectors.toList());
        PageImpl<PostListDto> postList = new PageImpl<>(postListDtos, pageable, posts.getTotalElements());
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<BasicResponse<PostGetDto>> getPost(@PathVariable Long postId) {
        Long memberId = customSecurityUtil.getMemberId();
        Post post = postService.findOne(postId);
        PostGetDto postDto = new PostGetDto(post, memberId);
        BasicResponse<PostGetDto> basicResponse = new BasicResponse<>("포스트 조회 완료", postDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.CREATED);
    }

    @PostMapping("/posts")
    public ResponseEntity<BasicResponse<PostDto>> createPost(@Valid @RequestBody PostRequestDto postRequestDto) {
        Long memberId = customSecurityUtil.getMemberId();
        Post post = postService.createPost(memberId, postRequestDto);
        PostDto postDto = new PostDto(post);
        BasicResponse<PostDto> basicResponse = new BasicResponse<>("포스트 생성 완료", postDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/posts/{postId}")
    @PreAuthorize("@customSecurityUtil.isPostOwner(#postId)")
    public ResponseEntity<BasicResponse<PostGetDto>> updatePost(@PathVariable Long postId,
                                                             @Valid @RequestBody PostRequestDto postRequestDto) {
        Long memberId = customSecurityUtil.getMemberId();
        Post updatePost = postService.updatePost(postId, postRequestDto);
        PostGetDto postDto = new PostGetDto(updatePost, memberId);
        BasicResponse<PostGetDto> basicResponse = new BasicResponse<>("포스트 업데이트 완료", postDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{postId}")
    @PreAuthorize("@customSecurityUtil.isPostOwner(#postId)")
    public ResponseEntity<BasicResponse> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        BasicResponse memberBasicResponse = new BasicResponse("게시글 삭제 완료", null);
        return new ResponseEntity<>(memberBasicResponse, HttpStatus.OK);
    }
}
