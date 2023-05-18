package hanseul.simpleBoard.exception.comment;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Long commentId) {
        super("존재하지 않는 댓글입니다. comment Id : " + commentId);
    }

}