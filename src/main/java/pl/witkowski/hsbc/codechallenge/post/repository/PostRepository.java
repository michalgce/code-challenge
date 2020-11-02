package pl.witkowski.hsbc.codechallenge.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.witkowski.hsbc.codechallenge.post.model.Post;

import java.util.Set;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    Page<Post> findAllByUserId(Long userId, Pageable pageable);
    Page<Post> findAllByUserIdIn(Set<Long> followersIds, Pageable pageable);
}
