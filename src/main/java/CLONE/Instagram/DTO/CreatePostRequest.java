package CLONE.Instagram.DTO;

public class CreatePostRequest {
    private String imageUrl;
    private String caption;

    public String getImageUrl(){
        return imageUrl;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl=imageUrl;
    }
    public String getCaption(){
    return caption;
    }
    public void setCaption(String caption){
        this.caption=caption;
    }
}

