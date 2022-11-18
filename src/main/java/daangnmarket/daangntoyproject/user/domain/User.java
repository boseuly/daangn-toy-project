package daangnmarket.daangntoyproject.user.domain;

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
    private String userId;

    @Column(name = "user_password")
    private String userPassword;

    private String nickname;

    private String email;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "manner_temp")
    private double mannerTemp;

    @Column(name = "region_cnt")
    private int regionCnt;

    private boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

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
                int regionCnt){
        this.userId = userId;
        this.userPassword = userPassword;
        this.nickname = nickname;
        this.email = email;
        this.imgUrl = imgUrl;
        this.mannerTemp = mannerTemp;
        this.regionCnt = regionCnt;
    }

}
