package CLONE.Instagram.DTO;

public class UserProfileResponse {

    private String username;
    private String bio;
    private String profilePicUrl;
    String error;

    public UserProfileResponse(String username, String bio, String profilePicUrl) {
        this.username = username;
        this.bio = bio;
        this.profilePicUrl = profilePicUrl;
    }
    public UserProfileResponse(String error) {
        this.error = error;
    }
    public String getUsername(){
        return username;
    }
    public String getBio(){
        return bio;
    }
    public String getProfilePicUrl(){
        return profilePicUrl;
    }
}

