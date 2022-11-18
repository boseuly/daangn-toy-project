package daangnmarket.daangntoyproject.user.model;

import daangnmarket.daangntoyproject.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {
    private String userId;
    private String userPassword;
    private String nickname;
    private String email;
    private String imgUrl;
    private boolean enabled;


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
    public UserDto(User entity){
        this.userId = entity.getUserId();
        this.userPassword = entity.getUserPassword();
    }


}
