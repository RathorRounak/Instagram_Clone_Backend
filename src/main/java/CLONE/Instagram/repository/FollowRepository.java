package CLONE.Instagram.repository;


import CLONE.Instagram.entity.Follow;
import CLONE.Instagram.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowing(Users follower, Users following);

    void deleteByFollowerAndFollowing(Users follower, Users following);

    List<Follow> findByFollower(Users follower);
    List<Follow> findByFollowing(Users following);
}
