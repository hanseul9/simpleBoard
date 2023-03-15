package hanseul.simpleBoard.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasicResponse<T> {

    private String message;
    private T data;
}
