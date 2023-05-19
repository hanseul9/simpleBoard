package hanseul.simpleBoard.requestdto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentUpdateRequestDto {
    private String content;
}
