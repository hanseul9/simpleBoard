package hanseul.simpleBoard.requestdto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberUpdateDto {
    @Email(message =  "email 형식을 맞춰주세요.")
    private String email;
    @Size(min = 6, message = "비밀번호는 6자 이상 입력해주세요.")
    private String password;
    private String name;
}
