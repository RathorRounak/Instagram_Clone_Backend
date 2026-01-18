package CLONE.Instagram.DTO;

public class ProfileEdit {
    String bio;
    String pfPhoto;

    public ProfileEdit(String bio, String pfPhoto){
        this.bio = bio;
        this.pfPhoto = pfPhoto;

    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getPfPhoto() {
        return pfPhoto;
    }
    public void setPfPhoto(String pfPhoto) {
        this.pfPhoto = pfPhoto;
    }
}
