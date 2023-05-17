package hanseul.simpleBoard.exception.member;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException () {
        super("이미 존재하는 email 입니다.");
    }

}