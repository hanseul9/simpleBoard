package hanseul.simpleBoard.responsedto.member;

import hanseul.simpleBoard.domain.Comment;
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
public class MemberGetResponseDto {

    private Long id;
    private String name;
    private String email;


    public MemberGetResponseDto(Member member) {
        id = member.getId();
        name = member.getName();
        email = member.getEmail();
    }
}
