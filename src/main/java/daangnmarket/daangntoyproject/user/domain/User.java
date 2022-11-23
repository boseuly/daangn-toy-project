package daangnmarket.daangntoyproject.user.domain;

import daangnmarket.daangntoyproject.board.domain.Post;
import daangnmarket.daangntoyproject.user.model.UserDto;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "tb_users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    @Column(name = "user_password")
    private String userPassword;

    private String nickname;

    private String email;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "manner_temp")
    private double mannerTemp;

    @Column(name = "region_id")
    private int regionId;

    private boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    // 사용자가 작성한 게시글 조회할 때 사용
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<Post>();


    public User(){}
    public User(UserDto userDto){
        this.userId = userDto.getUserId();
        this.userPassword = userDto.getUserPassword();
        this.nickname = userDto.getNickname();
        this.email = userDto.getEmail();
        this.enabled = userDto.isEnabled();
        this.mannerTemp = 36.5;
    }
    public User(String userId, String userPassword, String nickname, String email, String image, boolean enabled){
        this.userId = userId;
        this.userPassword = userPassword;
        this.nickname = nickname;
        this.email = email;
        this.imgUrl = image;
        this.enabled = enabled;
    }

    @Builder
    public User(String userId, String userPassword,
                String nickname, String email, String imgUrl, double mannerTemp,
                int regionId){
        this.userId = userId;
        this.userPassword = userPassword;
        this.nickname = nickname;
        this.email = email;
        this.imgUrl = imgUrl;
        this.mannerTemp = mannerTemp;
        this.regionId = regionId;
    }
    public User(String userId){
        this.userId = userId;
    }

}
