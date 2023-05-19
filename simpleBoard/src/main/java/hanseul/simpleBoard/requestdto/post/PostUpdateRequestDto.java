package hanseul.simpleBoard.requestdto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequestDto {
    @Size(max = 100, message = "제목이 너무 깁니다. 영어 기준 100자, 한글 기준 30자 까지 허용")
    private String title;

    private String content;

}
