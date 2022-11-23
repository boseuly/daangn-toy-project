package daangnmarket.daangntoyproject.board.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class postDto {
    // 게시글 리스트 가져올 때 필요한 객체
    private int postId;
    private String postTitle;

    private String region;
    private String imgUrl;  // 이건 post객체와 img객체를 연결해서 가져오기

    public postDto(){}

    public postDto(int postId, String postTitle, String region, String imgUrl) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.region = region;
        this.imgUrl = imgUrl;
    }
}
