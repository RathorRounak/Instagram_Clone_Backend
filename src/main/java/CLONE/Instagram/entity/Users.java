package CLONE.Instagram.entity;
import jakarta.persistence.*;

@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(unique=true, nullable=false)
    private String username;
    @Column(nullable=false)
    private String password;
    @Column(length=105)
    String bio;
    @Column
    String pfPhoto;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username= username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password= password;
    }
    public void setBio(String bio){
        this.bio=bio;
    }
    public String getBio() {
        return bio;
    }
    public String getPfPhoto() {
        return pfPhoto;
    }
    public void setPfPhoto(String pfPhoto) {
        this.pfPhoto= pfPhoto;
    }
}
