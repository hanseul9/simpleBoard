package hanseul.simpleBoard.requestdto.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberCreateDto {
    private String email;
    private String password;
    private String username;


}
