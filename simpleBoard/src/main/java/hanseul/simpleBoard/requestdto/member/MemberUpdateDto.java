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
public class MemberUpdateDto {
    @Email(message =  "email 형식을 맞춰주세요.")
    private String email;

    @NotBlank(message = "새로운 비밀번호를 입력해 주세요.")
    @Size(min = 6, message = "비밀번호는 6자 이상 입력해주세요.")
    private String newPassword; //새로운 비밀번호

    @NotBlank(message = "기존 비밀번호를 입력해 주세요.")
    @Size(min = 6, message = "비밀번호는 6자 이상 입력해주세요.")
    private String oldPassword; //이전 비밀번호

    private String name;
}
