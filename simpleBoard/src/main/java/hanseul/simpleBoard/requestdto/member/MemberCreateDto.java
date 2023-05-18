package hanseul.simpleBoard.requestdto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateDto {

    @NotBlank(message = "email이 누락되었습니다.")
    @Email(message =  "email 형식을 맞춰주세요.")
    private String email;

    @Size(min = 6, message = "비밀번호는 6자 이상 입력해주세요.")
    @NotBlank(message = "비밀번호가 누락되었습니다.")
    private String password;
    @NotBlank(message = "회원이름이 누락되었습니다.")
    private String name;


}
