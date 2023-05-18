package hanseul.simpleBoard.service;

import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.exception.post.PostNotFoundException;
import hanseul.simpleBoard.repository.PostRepository;
import hanseul.simpleBoard.requestdto.post.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;

    public Post findOne(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    public Page<Post> findAllByOrderByPostedAtDescIdDesc(Pageable pageable) { // 전체 포스트 id 기준 오름차순
        return postRepository.findAllByOrderByPostedAtDescIdDesc(pageable);
    }

    public Page<Post> findAllByOrderByPostedAtDescIdAsc(Pageable pageable) { // 전체 포스트 id 기준내림차순
        return postRepository.findAllByOrderByPostedAtDescIdAsc(pageable);
    }

    public Page<Post> findByMemberIdOrderByPostedAtDescIdAsc(Pageable pageable, Long memberId) {
        // 특정 회원 포스트 id 기준 내림차순 + 패치조인
        return postRepository.findByMemberIdOrderByPostedAtDescIdAsc(memberId, pageable);
    }

    @Transactional
    public Post createPost(Long memberId, PostRequestDto postRequestDto) {
        Member member = memberService.findOne(memberId);
        Post post = new Post(postRequestDto.getTitle(), postRequestDto.getContent(), member);
        postRepository.save(post);
        return post;
    }

    @Transactional
    public Post updatePost(Long postId, PostRequestDto postRequestDto) {
        Post post = findOne(postId);

        post.update(postRequestDto.getTitle(), postRequestDto.getContent());

        return post;
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = findOne(postId);
        postRepository.delete(post);
    }

}
