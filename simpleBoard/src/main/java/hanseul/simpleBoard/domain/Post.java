package hanseul.simpleBoard.domain;

import hanseul.simpleBoard.requestdto.post.PostRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue
    @JoinColumn(name = "post_id")
    private Long id;
    private String title;
    private String content;
    private LocalDateTime postedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Post(String title, String content, Member member) {
        this.title = title;
        this.content = content;

        //양방향 연관관계
        this.member = member;
        member.getPostList().add(this);

        this.postedAt = LocalDateTime.now();
    }

    public void update(PostRequestDto postRequestDto) {
        if (postRequestDto.getTitle() != null) {
            this.title = postRequestDto.getTitle();
        }
        if (postRequestDto.getContent() != null) {
            this.content = postRequestDto.getContent();
        }
        this.postedAt = LocalDateTime.now();
    }
}
