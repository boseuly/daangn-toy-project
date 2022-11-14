package daangnmarket.daangntoyproject.user.model;

import daangnmarket.daangntoyproject.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@Getter
@NoArgsConstructor
public class UserDto {
    private String userId;
    private String userPassword;
    private String userName;
    private String nickname;
    private String gender;
    private String imgUrl;
    private double mannerTemp;
    private String phoneNum;
    private int regionCnt;


    public UserDto(String userId, String userPassword, String userName,
                   String nickname, String gender, String imgUrl, double mannerTemp,
                   String phoneNum, int regionCnt) {
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

    //dto -> entity (db에 등록할 때 사용)
    public User toEntity(){
        return User.builder()
                .userId(userId)
                .userName(userName)
                .userPassword(userPassword)
                .build();
    }

    //entity -> dto(db를 조회할 때 사용)
    public UserDto(User entity){
        this.userId = entity.getUserId();
        this.userName = entity.getUserName();
        this.userPassword = entity.getUserPassword();
    }



}
