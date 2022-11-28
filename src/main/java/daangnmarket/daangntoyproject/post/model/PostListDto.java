package daangnmarket.daangntoyproject.post.model;

import daangnmarket.daangntoyproject.post.domain.Image;
import daangnmarket.daangntoyproject.post.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PostListDto {
    // 게시글 리스트 가져올 때 필요한 객체
    private int postId;
    private String postTitle;

    private String region;

    private String price;
    private int likeCnt;
    private int viewCnt;

    private String imgUrl;

    public PostListDto(){}
    // Entity를 Dto로 변환해줘야 한다.
    public PostListDto(Post post){
        this.postId = post.getPostId();
        this.postTitle = post.getPostTitle();
        this.region = post.getRegion().getSecDepth()+ " "+ post.getRegion().getThirdDepth();
        this.price = String.format("%,d", post.getPrice());
        this.likeCnt = post.getLikeCnt();
        this.viewCnt = post.getViewCnt();
        this.imgUrl = post.topImageUrl();
    }
//    public PostListDto(List<Image> images){
//        int idx = 0;
//        this.imgUrl = String.valueOf(images.get(0));
//        System.out.println("이미지 url : " + this.imgUrl);
//    }

    public PostListDto(int postId, String postTitle, String region, String price, int likeCnt, int viewCnt, List<Image> images) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.region = region;
        this.price = String.format("%,d", price);
        this.likeCnt = likeCnt;
        this.viewCnt = viewCnt;
        // 이미지 저장
    }
}
