package hanseul.simpleBoard.responsedto.post;

import hanseul.simpleBoard.domain.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostGetDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime postedAt;
    private String writerName;
    private boolean owner = false;

    public PostGetDto(Post post, Long memberId) {
        id = post.getId();
        title = post.getTitle();
        content = post.getContent();
        postedAt = post.getPostedAt();
        writerName = post.getMember().getName();
        owner  = post.getMember().getId().equals(memberId);
    }
}
