package hanseul.simpleBoard;


import hanseul.simpleBoard.domain.Comment;
import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.repository.CommentRepository;
import hanseul.simpleBoard.repository.MemberRepository;
import hanseul.simpleBoard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import hanseul.simpleBoard.requestdto.comment.CommentRequestDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Profile("dev") //테스트시 실행X
public class InitDb implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    final private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData() {
        // Create sample members
        String encodedPassword = passwordEncoder.encode("password4");
        Member member1 = new Member("member1", "test1@naver.com", encodedPassword);
        Member member2 = new Member("Alice", "alice@example.com", encodedPassword);
        Member member3= new Member("Bob", "bob@example.com", encodedPassword);

        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);
        member3 = memberRepository.save(member3);

        // Create sample posts
        Post post1 = new Post("111", "포스팅1", member1);
        Post post2 = new Post("222", "포스팅2", member2);
        Post post3 = new Post("333", "포스팅3", member3);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        CommentRequestDto commentDto
                = new CommentRequestDto("member1이 쓸 댓글 내용입니다.");

        Comment comment = new Comment(post1, member1, commentDto.getContent());
        commentRepository.save(comment);

//        for (int i = 1; i <= 20; i++) { //더미데이터 생성
//            postRepository.save(new Post("Hello world!", "포스팅1", member1));
//
//        }

    }
}
