package hanseul.simpleBoard.service;

import hanseul.simpleBoard.domain.Comment;
import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.exception.post.PostNotFoundException;
import hanseul.simpleBoard.repository.CommentRepository;
import hanseul.simpleBoard.repository.PostRepository;
import hanseul.simpleBoard.requestdto.post.PostCreateRequestDto;
import hanseul.simpleBoard.requestdto.post.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.DoubleStream;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;

    private final CommentRepository commentRepository;

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

    public Page<Post> findByMemberIdOrderByPostedAtDescIdDESC(Pageable pageable, Long memberId) {
        // 특정 회원 포스트 id 기준 오름차순
        return postRepository.findByMemberIdOrderByPostedAtDescIdDesc(memberId, pageable);
    }


    @Transactional
    public Post createPost(Long memberId, PostCreateRequestDto postCreateRequestDto) {
        Member member = memberService.findOne(memberId);
        Post post = new Post(postCreateRequestDto.getTitle(), postCreateRequestDto.getContent(), member);
        postRepository.save(post);
        return post;
    }

    @Transactional
    public Post updatePost(Long postId, PostUpdateRequestDto postUpdateRequestDto) {
        Post post = findOne(postId);

        post.update(postUpdateRequestDto.getTitle(), postUpdateRequestDto.getContent());

        return post;
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = findOne(postId);
        postRepository.delete(post);
    }


}
