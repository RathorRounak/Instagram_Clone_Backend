package CLONE.Instagram.entity;

import jakarta.persistence.*;
import jakarta.validation.Constraint;

import java.time.LocalDateTime;

@Entity
@Table(
        name="likes",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "post_id"}
        )
)
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Post getPost(){
        return post;
    }
    public void setPost(Post post){
        this.post = post;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
   public void  setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
