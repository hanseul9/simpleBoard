package hanseul.simpleBoard.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue
    @JoinColumn(name = "comment_id")
    private Long id;
    private String content;
    private LocalDateTime commentedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(Post post, Member member, String content) {
        this.post = post;
        this.member = post.getMember();
        this.commentedAt = LocalDateTime.now();
        this.content = content;

        post.getCommentList().add(this);
        member.getCommentList().add(this);


    }

    public void update(String content) {
        if (content != null) {
            this.content = content;
        }
        this.commentedAt = LocalDateTime.now();
    }
}
