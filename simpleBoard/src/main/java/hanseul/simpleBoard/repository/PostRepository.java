package hanseul.simpleBoard.repository;

import hanseul.simpleBoard.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PagingAndSortingRepository<Post, Long> {

    Page<Post> findAllByOrderByPostedAtDesc(Pageable pageable);
}
