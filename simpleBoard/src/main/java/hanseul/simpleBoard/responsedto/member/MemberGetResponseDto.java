package hanseul.simpleBoard.responsedto.member;

import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.responsedto.post.PostDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MemberGetResponseDto {

    private Long id;
    private String name;
    private String email;
    private List<PostDto> postList;

    public MemberGetResponseDto(Member member) {
        id = member.getId();
        name = member.getName();
        email = member.getEmail();
        postList = member.getPostList().stream()
                .map(post -> new PostDto(post)).collect(Collectors.toList());
    }
}
