package hanseul.simpleBoard;


import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.repository.MemberRepository;
import hanseul.simpleBoard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class InitDb implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    @Override
    public void run(String... args) throws Exception {
        loadData();

    }

    private void loadData() {
        // Create sample members
        Member member1 = new Member("Alice", "alice@example.com", "password123");
        Member member2 = new Member("Bob", "bob@example.com", "password456");

        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);

        // Create sample posts
        Post post1 = new Post("Hello world!", "포스팅1", member1);
        Post post2 = new Post("Greetings!", "포스팅2", member2);

        postRepository.save(post1);
        postRepository.save(post2);

        for (int i = 1; i <= 100; i++) { //더미데이터 생성
            postRepository.save(new Post("Hello world!", "포스팅1", member1));
        }

    }
}
