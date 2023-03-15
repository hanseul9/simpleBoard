package hanseul.simpleBoard.controller;

import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.responsedto.post.PostListDto;
import hanseul.simpleBoard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostController {

    private static final int SUCCESS = 200;
    private static final int CREATED = 201;

    private final PostService postService;

    @GetMapping("/api/posts")
    public Page<PostListDto> getPostTitles(@RequestParam(value = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Post> posts = postService.findAllByOrderByPostedAtDesc(pageable);
        List<PostListDto> postListDtos = posts.getContent().stream()
                .map(PostListDto::new)
                .collect(Collectors.toList());
        return new PageImpl<>(postListDtos, pageable, posts.getTotalElements());
    }


}
