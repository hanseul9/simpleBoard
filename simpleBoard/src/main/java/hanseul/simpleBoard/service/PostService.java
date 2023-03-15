package hanseul.simpleBoard.service;

import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    public Page<Post> findAllByOrderByPostedAtDesc(Pageable pageable) {
        return postRepository.findAllByOrderByPostedAtDesc(pageable);
    }
}
