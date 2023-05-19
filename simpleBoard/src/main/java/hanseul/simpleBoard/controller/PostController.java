package hanseul.simpleBoard.controller;

import hanseul.simpleBoard.config.auth.CustomSecurityUtil;
import hanseul.simpleBoard.domain.Comment;
import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.requestdto.comment.CommentCreateRequestDto;
import hanseul.simpleBoard.requestdto.comment.CommentUpdateRequestDto;
import hanseul.simpleBoard.requestdto.post.PostCreateRequestDto;
import hanseul.simpleBoard.requestdto.post.PostUpdateRequestDto;
import hanseul.simpleBoard.responsedto.BasicResponse;
import hanseul.simpleBoard.responsedto.comment.CommentGetResponseDto;
import hanseul.simpleBoard.responsedto.post.PostDto;
import hanseul.simpleBoard.responsedto.post.PostListDto;
import hanseul.simpleBoard.responsedto.post.PostGetDto;
import hanseul.simpleBoard.service.CommentService;
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
    private final CommentService commentService;
    private final CustomSecurityUtil customSecurityUtil;

    @GetMapping("/posts") // 게시글들 조회
    public ResponseEntity<Page<PostListDto>> getPostTitles(@RequestParam(value = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<PostListDto> postList = postService.findAllByOrderByPostedAtDescIdDesc(pageable)
                .map(PostListDto::new);
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}") // 게시글 단건조회
    public ResponseEntity<BasicResponse<PostGetDto>> getPost(@PathVariable Long postId) {
        Long memberId = customSecurityUtil.getMemberId();
        Post post = postService.findOne(postId);
        PostGetDto postDto = new PostGetDto(post, memberId);
        BasicResponse<PostGetDto> basicResponse = new BasicResponse<>("포스트 조회 완료", postDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.CREATED);
    }

    @PostMapping("/posts") // 게시글 작성
    public ResponseEntity<BasicResponse<PostDto>> createPost(@Valid @RequestBody PostCreateRequestDto postCreateRequestDto) {
        Long memberId = customSecurityUtil.getMemberId();
        Post post = postService.createPost(memberId, postCreateRequestDto);
        PostDto postDto = new PostDto(post);
        BasicResponse<PostDto> basicResponse = new BasicResponse<>("포스트 생성 완료", postDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/posts/{postId}") // 게시글 수정
    @PreAuthorize("@customSecurityUtil.isPostOwner(#postId)")
    public ResponseEntity<BasicResponse<PostGetDto>> updatePost(@PathVariable Long postId,
                                                             @Valid @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        Long memberId = customSecurityUtil.getMemberId();
        Post updatePost = postService.updatePost(postId, postUpdateRequestDto);
        PostGetDto postDto = new PostGetDto(updatePost, memberId);
        BasicResponse<PostGetDto> basicResponse = new BasicResponse<>("포스트 업데이트 완료", postDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{postId}") // 게시글 삭제
    @PreAuthorize("@customSecurityUtil.isPostOwner(#postId)")
    public ResponseEntity<BasicResponse> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        BasicResponse memberBasicResponse = new BasicResponse("게시글 삭제 완료", null);
        return new ResponseEntity<>(memberBasicResponse, HttpStatus.OK);
    }

    //-----------------------------

    /*
    댓글
     */

    @PostMapping("/posts/{postId}/comments") // 댓글 달기
    public ResponseEntity<BasicResponse<CommentGetResponseDto>> createComment(@PathVariable Long postId,
                                                                              @Valid @RequestBody CommentCreateRequestDto commentDto) {

        Long memberId = customSecurityUtil.getMemberId();
        Member member = memberService.findOne(memberId);
        Post post = postService.findOne(postId);
        Comment comment = commentService.createComment(post, member, commentDto);
        CommentGetResponseDto commentResponseDto = new CommentGetResponseDto(comment);

        BasicResponse<CommentGetResponseDto> basicResponse
                = new BasicResponse<>("댓글 생성 완료", commentResponseDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}") //댓글 삭제
    @PreAuthorize("@customSecurityUtil.isCommentOwner(#commentId)")
    public ResponseEntity<BasicResponse> deleteComment(@PathVariable Long postId,
                                                         @PathVariable Long commentId) {

        commentService.deleteComment(commentId);
        BasicResponse memberBasicResponse = new BasicResponse("게시글 삭제 완료", null);
        return new ResponseEntity<>(memberBasicResponse, HttpStatus.OK);
    }

    @PatchMapping("/posts/{postId}/comments/{commentId}") //댓글 수정
    @PreAuthorize("@customSecurityUtil.isCommentOwner(#commentId)")
    public ResponseEntity<BasicResponse> updateComment(@PathVariable Long postId,
                                                       @PathVariable Long commentId,
                                                       @Valid @RequestBody CommentUpdateRequestDto commentDto) {

        Comment comment = commentService.updateComment(commentId, commentDto);
        CommentGetResponseDto commentResponseDto = new CommentGetResponseDto(comment);
        BasicResponse<CommentGetResponseDto> basicResponse
                = new BasicResponse<>("댓글 수정 완료", commentResponseDto);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }


}
