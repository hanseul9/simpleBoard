package hanseul.simpleBoard.requestdto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostRequestDto {

    @Size(max = 100, message = "제목이 너무 깁니다. 영어 기준 100자, 한글 기준 30자 까지 허용")
    @NotBlank(message = "제목이 누락되었습니다.")
    private String title;

    @NotBlank(message = "내용이 누락되었습니다.")
    private String content;
}
