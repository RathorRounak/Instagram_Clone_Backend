package CLONE.Instagram.entity;

import jakarta.persistence.*;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"following_id","follower_id"}))
public class Follow {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="follower_id",nullable=false)
    private Users follower;
    @ManyToOne
    @JoinColumn(name="following_id",nullable=false)
    private Users following;

    public Users getFollower() {
        return follower;
    }
    public void setFollower(Users follower) {
        this.follower = follower;
    }
    public Users getFollowing() {
        return following;
    }
    public void setFollowing(Users following) {
        this.following = following;
    }

}
