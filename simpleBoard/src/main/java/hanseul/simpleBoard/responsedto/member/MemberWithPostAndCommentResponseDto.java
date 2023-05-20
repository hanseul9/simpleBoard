package hanseul.simpleBoard.responsedto.member;

import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.responsedto.comment.CommentGetResponseDto;
import hanseul.simpleBoard.responsedto.post.PostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberWithPostAndCommentResponseDto {
    private Long id;
    private String name;
    private String email;
    private List<PostDto> postList;
    private List<CommentGetResponseDto> commentList;

    public MemberWithPostAndCommentResponseDto(Member member) {
        id = member.getId();
        name = member.getName();
        email = member.getEmail();
        postList = member.getPostList().stream()
                .map(PostDto::new).collect(Collectors.toList());
        commentList = member.getCommentList().stream()
                .map(CommentGetResponseDto::new).collect(Collectors.toList());
    }
}
