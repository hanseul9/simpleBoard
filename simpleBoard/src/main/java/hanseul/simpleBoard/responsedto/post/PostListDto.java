package hanseul.simpleBoard.responsedto.post;

import hanseul.simpleBoard.domain.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostListDto {
    private Long id;
    private String title;
    private LocalDateTime postedAt;
    private String writerName;
    public PostListDto (Post post) {
        id = post.getId();
        title = post.getTitle();
        postedAt = post.getPostedAt();
        writerName = post.getMember().getName();
    }
}
