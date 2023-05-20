package hanseul.simpleBoard.responsedto.comment;

import hanseul.simpleBoard.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentInPostResponseDto { //게시글의 댓글까지 함게 조회할때 사용
    private Long id;
    private String content;
    private LocalDateTime commentedAt;
    private String writerName;
    private boolean owner = false;

    public CommentInPostResponseDto(Comment comment, Long memberId) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.commentedAt = comment.getCommentedAt();
        this.writerName = comment.getMember().getName();
        this.owner  = comment.getMember().getId().equals(memberId);
    }
}
