package hanseul.simpleBoard.responsedto.post;

import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.responsedto.comment.CommentGetResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PostListDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime postedAt;
    private String writerName;

    public PostListDto (Post post) {
        id = post.getId();
        title = post.getTitle();
        content = post.getContent();
        postedAt = post.getPostedAt();
        writerName = post.getMember().getName();
    }
}
