package hanseul.simpleBoard.exception.member;

public class MemberPasswordIncorrectException extends RuntimeException {
    public MemberPasswordIncorrectException() {
        super("비밀번호가 틀렸습니다.");
    }

}