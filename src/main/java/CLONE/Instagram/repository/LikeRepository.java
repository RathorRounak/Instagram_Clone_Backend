package CLONE.Instagram.repository;

import CLONE.Instagram.entity.Like;
import CLONE.Instagram.entity.Post;
import CLONE.Instagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndPost(User user, Post post);

    void deleteByUserAndPost(User user, Post post);

    long countByPost(Post post);
}
