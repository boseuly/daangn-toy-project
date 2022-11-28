package daangnmarket.daangntoyproject.user.model;

import daangnmarket.daangntoyproject.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {
    private String userId;
    private String userPassword;
    private String nickname;
    private String email;       // email 인증
    private String imgUrl;
    private double mannerTemp;
    private boolean enabled;
    private String authKey;     // email 인증
    private int authStatus;     // email 인증


    public UserDto(String userId, String userPassword, String nickname
                , String imgUrl, String email) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.nickname = nickname;
        this.imgUrl = imgUrl;
        this.email = email;
    }

    //dto -> entity (db에 등록할 때 사용)
    public User toEntity(){
        return User.builder()
                .userId(userId)
                .userPassword(userPassword)
                .nickname(nickname)
                .email(email)
                .imgUrl(imgUrl)
                .build();
    }

    //entity -> dto(db를 조회할 때 사용)
    public UserDto(Optional<User> entity){
        this.userId = entity.get().getUserId();
        this.userPassword = entity.get().getUserPassword();
        this.email = entity.get().getEmail();
        this.mannerTemp = entity.get().getMannerTemp();
        this.nickname = entity.get().getNickname();
        this.imgUrl = entity.get().getImgUrl();
    }


}
