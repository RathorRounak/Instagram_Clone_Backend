package CLONE.Instagram.repository;

import CLONE.Instagram.entity.Post;
import CLONE.Instagram.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Postrepository extends JpaRepository<Post, Long> {

    List<Post> findByUsersOrderByCreatedAtDesc(Users user);
    Post findById(long id);
}
