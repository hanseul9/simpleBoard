package hanseul.simpleBoard.responsedto.post;

import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.responsedto.comment.CommentGetResponseDto;
import hanseul.simpleBoard.responsedto.comment.CommentInPostResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PostGetDto {   // 단건 조회시 사용
    private Long id;
    private String title;
    private String content;
    private LocalDateTime postedAt;
    private String writerName;
    private boolean owner = false;

    private List<CommentInPostResponseDto > commentList = new ArrayList<>();

    public PostGetDto(Post post, Long memberId) {
        id = post.getId();
        title = post.getTitle();
        content = post.getContent();
        postedAt = post.getPostedAt();
        writerName = post.getMember().getName();
        owner  = post.getMember().getId().equals(memberId);

        this.commentList = post.getCommentList().stream()
                .map(comment -> new CommentInPostResponseDto(comment, memberId))
                .collect(Collectors.toList());

    }
}
