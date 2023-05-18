package hanseul.simpleBoard.service;

import hanseul.simpleBoard.domain.Comment;
import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.domain.Post;
import hanseul.simpleBoard.exception.comment.CommentNotFoundException;
import hanseul.simpleBoard.repository.CommentRepository;
import hanseul.simpleBoard.requestdto.comment.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment findOne(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException(commentId));
    }

    @Transactional
    public Comment createComment(Post post, Member member, CommentRequestDto commentDto) {

        Comment comment = new Comment(post, member, commentDto.getContent());
        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = findOne(commentId);
        commentRepository.delete(comment);
    }

    @Transactional
    public Comment updateComment(Long commentId, CommentRequestDto createDto) {
        Comment comment = findOne(commentId);
        comment.update(createDto.getContent());
        return comment;
    }
}
