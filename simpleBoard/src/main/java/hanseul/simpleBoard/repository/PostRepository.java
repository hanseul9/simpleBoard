package hanseul.simpleBoard.repository;

import hanseul.simpleBoard.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>,
        PagingAndSortingRepository<Post, Long> {

    Page<Post> findAllByOrderByPostedAtDescIdDesc(Pageable pageable);
    Page<Post> findAllByOrderByPostedAtDescIdAsc(Pageable pageable);


    @Query("SELECT p FROM Post p WHERE p.member.id = :memberId ORDER BY p.postedAt ASC")
    Page<Post> findByMemberIdOrderByPostedAtDescIdAsc(Long memberId, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN FETCH p.member WHERE p.id IN :ids")
    List<Post> findAllByIdsWithFetchJoin(Collection<Long> ids);

}
