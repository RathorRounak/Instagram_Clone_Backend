package CLONE.Instagram.entity;
import jakarta.persistence.*;

@Entity
@Table(name="photos")
public class Photos {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column
    private String photoPath;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private Users user;
}
