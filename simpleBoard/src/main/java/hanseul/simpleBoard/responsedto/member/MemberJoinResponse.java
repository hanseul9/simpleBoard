package hanseul.simpleBoard.responsedto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class MemberJoinResponse {
    private Long id;
    private Integer status;
    private String message;
}

