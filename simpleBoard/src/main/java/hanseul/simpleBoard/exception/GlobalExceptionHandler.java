package hanseul.simpleBoard.exception;

import hanseul.simpleBoard.exception.comment.CommentNotFoundException;
import hanseul.simpleBoard.exception.member.DuplicateEmailException;
import hanseul.simpleBoard.exception.member.MemberNotFoundException;
import hanseul.simpleBoard.exception.member.MemberPasswordIncorrectException;
import hanseul.simpleBoard.exception.post.PostNotFoundException;
import hanseul.simpleBoard.responsedto.ErrorBasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class) //접근 예외
    public ResponseEntity<ErrorBasicResponse> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

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

    @ExceptionHandler(MemberNotFoundException.class) //회원찾기 실패
    public ResponseEntity<ErrorBasicResponse> handleMemberNotFoundException(MemberNotFoundException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberPasswordIncorrectException.class) //회원 비밀번호 오류
    public ResponseEntity<ErrorBasicResponse> handleMemberPasswordIncorrectException(MemberPasswordIncorrectException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PostNotFoundException.class) //게시글 찾기 실패
    public ResponseEntity<ErrorBasicResponse> handlePostNotFoundException(PostNotFoundException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class) //댓글 찾기 실패
    public ResponseEntity<ErrorBasicResponse> handleCommentNotFoundException(CommentNotFoundException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEmailException.class) //이메일 중복 예외
    public ResponseEntity<ErrorBasicResponse>  handleDuplicateEmailException( DuplicateEmailException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
