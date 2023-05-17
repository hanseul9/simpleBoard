package hanseul.simpleBoard.exception;

import hanseul.simpleBoard.exception.member.DuplicateEmailException;
import hanseul.simpleBoard.exception.member.MemberNotFoundException;
import hanseul.simpleBoard.exception.post.PostNotFoundException;
import hanseul.simpleBoard.responsedto.ErrorBasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) // Valid 예외
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorBasicResponse> handleMemberNotFoundException(MemberNotFoundException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorBasicResponse> handleMemberNotFoundException(PostNotFoundException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorBasicResponse>  handleDuplicateEmailException( DuplicateEmailException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
