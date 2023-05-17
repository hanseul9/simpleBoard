package hanseul.simpleBoard.exception.post;

public class PostNotFoundException  extends RuntimeException {
    public PostNotFoundException(Long postId) {
        super("존재하지 않는 포스트 입니다. post Id : " + postId);
    }

}