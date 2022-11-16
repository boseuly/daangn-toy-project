package daangnmarket.daangntoyproject.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_name")
    private String userName;

    private String nickname;

    private String gender;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "manner_temp")
    private double mannerTemp;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "region_cnt")
    private int regionCnt;

    @ManyToMany
    @JoinTable(
            name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @Builder
    public User(String userId, String userPassword, String userName,
                String nickname, String gender, String imgUrl, double mannerTemp,
                String phoneNum, int regionCnt){
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.nickname = nickname;
        this.gender = gender;
        this.imgUrl = imgUrl;
        this.mannerTemp = mannerTemp;
        this.phoneNum = phoneNum;
        this.regionCnt = regionCnt;
    }

}
