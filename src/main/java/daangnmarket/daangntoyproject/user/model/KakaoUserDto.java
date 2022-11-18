package daangnmarket.daangntoyproject.user.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(exclude = "KakaoUserDto")
public class KakaoUserDto {
    private long id;
    private String email;
    private String nickname;
    private String image;

    public KakaoUserDto(long id, String email, String nickname, String image){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.image = image;
    }

}
