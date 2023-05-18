package hanseul.simpleBoard.responsedto.post;

import hanseul.simpleBoard.domain.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime postedAt;
    private String writerName;


    public PostDto(Post post) {
        id = post.getId();
        title = post.getTitle();
        content = post.getContent();
        postedAt = post.getPostedAt();
        writerName = post.getMember().getName();

    }
}
