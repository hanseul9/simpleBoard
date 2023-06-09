package hanseul.simpleBoard;

import hanseul.simpleBoard.domain.Comment;
import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.repository.CommentRepository;
import hanseul.simpleBoard.repository.MemberRepository;
import hanseul.simpleBoard.repository.PostRepository;
import hanseul.simpleBoard.requestdto.comment.CommentCreateRequestDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
@Profile("dev") //테스트시 실행X
public class InitDB {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    final private PasswordEncoder passwordEncoder;


    @Transactional
    @PostConstruct
    public void dataInit() {
        String encodedPassword = passwordEncoder.encode("password4");
        Member member1 = new Member("member1", "test1@naver.com", encodedPassword);
        Member member2 = new Member("Alice", "alice@example.com", encodedPassword);
        Member member3= new Member("Bob", "bob@example.com", encodedPassword);

        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);
        member3 = memberRepository.save(member3);

        Post post1 = new Post("제목1", "포스팅1", member1);
        Post post2 = new Post("제목2", "포스팅2", member2);
        Post post3 = new Post("제목3", "포스팅3", member3);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);


        Post post4= new Post("제목입니다", "포스팅1", member1);
        postRepository.save(post4);


        CommentCreateRequestDto commentDto
                = new CommentCreateRequestDto("member1이 쓸 댓글 내용입니다.");
        for (int i = 1; i <= 200; i++) { //더미데이터 생성
            postRepository.save(new Post("Hello world! "+i, "포스팅1 "+i, member1));
            commentRepository.save( new Comment(post1, member1, commentDto.getContent()));

        }
        for (int i = 1; i <= 20; i++) { //더미데이터 생성
            postRepository.save(new Post("Hello world! "+i, "포스팅2 "+i, member2));

        }
        for (int i = 1; i <= 20; i++) { //더미데이터 생성
            postRepository.save(new Post("Hello world! "+i, "포스팅3 "+i, member3));

        }



    }



}
