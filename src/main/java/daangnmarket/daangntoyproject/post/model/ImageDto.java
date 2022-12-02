package daangnmarket.daangntoyproject.post.model;

import daangnmarket.daangntoyproject.post.domain.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImageDto {
    private int imgId;
    private int postId;
    private String imgName;
    private String imgUrl;

    public Image toEntity (){
        return Image.builder()
                .postId(postId)
                .imgName(imgName)
                .imgUrl(imgUrl)
                .build();
    }

    public ImageDto(Image image){
        this.imgId = image.getImgId();
        this.imgUrl = image.getImgUrl();
        this.imgName = image.getImgName();
        this.postId = image.getPostId();
    }

}
