package hanseul.simpleBoard.responsedto.comment;

import hanseul.simpleBoard.domain.Comment;
import hanseul.simpleBoard.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentGetResponseDto {

    private Long id;
    private String content;
    private LocalDateTime commentedAt;
    private String writerName;

    public CommentGetResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.commentedAt = comment.getCommentedAt();
        this.writerName = comment.getMember().getName();
    }


}
