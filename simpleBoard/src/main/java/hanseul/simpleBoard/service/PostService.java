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

    public Page<Post> findAllByOrderByPostedAtDesc(Pageable pageable) {
        return postRepository.findAllByOrderByPostedAtDesc(pageable);
    }

    @Transactional
    public Long createPost(Long memberId, PostRequestDto postRequestDto) {
        Member member = memberService.findOne(memberId);
        Post post = new Post(postRequestDto.getTitle(), postRequestDto.getContent(), member);
        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public Long updatePost(Long postId, PostRequestDto postRequestDto) {
        Post post = findOne(postId);

        post.update(postRequestDto);

        return post.getId();
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = findOne(postId);
        postRepository.delete(post);
    }

}
