package hanseul.simpleBoard.responsedto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinMemberResponse {
    private Long id;
    private Integer status;
    private String message;
}

