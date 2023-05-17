package hanseul.simpleBoard.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorBasicResponse {
    private int status;
    private String message;
}
