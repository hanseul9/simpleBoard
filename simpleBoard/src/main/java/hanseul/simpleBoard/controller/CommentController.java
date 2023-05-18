package hanseul.simpleBoard.controller;

import hanseul.simpleBoard.config.auth.CustomSecurityUtil;
import hanseul.simpleBoard.domain.Comment;
import hanseul.simpleBoard.responsedto.BasicResponse;
import hanseul.simpleBoard.responsedto.comment.CommentGetResponseDto;
import hanseul.simpleBoard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private static final int SUCCESS = 200;
    private static final int CREATED = 201;
    private final CommentService commentService;
    private final CustomSecurityUtil customSecurityUtil;

    @GetMapping("/comments/{commentId}") // 댓글 단건 조회
    public ResponseEntity<BasicResponse<CommentGetResponseDto>> getComment(@PathVariable Long commentId) {
        Comment comment = commentService.findOne(commentId);
        CommentGetResponseDto commentGetResponseDto = new CommentGetResponseDto(comment);
        BasicResponse<CommentGetResponseDto> response = new BasicResponse<>("댓글 조회 성공", commentGetResponseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
