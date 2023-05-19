package hanseul.simpleBoard.requestdto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequestDto {
    @NotBlank(message = "댓글 내용이 비어있습니다.")
    private String content;
}
