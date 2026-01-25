package CLONE.Instagram.repository;

import CLONE.Instagram.entity.Post;
import CLONE.Instagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Postrepository extends JpaRepository<Post, Long> {

    List<Post> findByUserOrderByCreatedAtDesc(User user);
    Post findById(long id);
    @Query("""
    SELECT p FROM Post p
    WHERE p.user IN :users
    ORDER BY p.createdAt DESC
""")
    List<Post> findFeedPosts(@Param("users") List<User> users);

}
