package CLONE.Instagram.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="photos")
public class Post {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String photoPath;
    @Column(length=105)
    private String caption;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private Users user;

    private LocalDateTime createAt;

    public String  getPhotoPath() {
        return photoPath;
    }
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
    public LocalDateTime getCreateAt() {
        return createAt;
    }
    public void setCreatedAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
    public Users getUser(){
        return user;
    }
    public void setUser(Users user){
        this.user = user;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getCreatedAt() {
        return createAt;
    }

}
