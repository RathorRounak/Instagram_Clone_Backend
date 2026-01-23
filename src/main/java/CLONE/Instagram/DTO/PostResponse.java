package CLONE.Instagram.DTO;

import java.time.LocalDateTime;

public class PostResponse {

    private Long id;
    private String caption;
    private String imageUrl;
    private LocalDateTime createdAt;

    public PostResponse(Long id, String caption, String imageUrl, LocalDateTime createdAt) {
        this.id = id;
        this.caption = caption;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
    public String getImageUrl() {
    return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

