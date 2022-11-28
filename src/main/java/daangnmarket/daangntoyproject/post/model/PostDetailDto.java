package daangnmarket.daangntoyproject.post.model;

import daangnmarket.daangntoyproject.post.domain.Image;
import daangnmarket.daangntoyproject.post.domain.Post;
import daangnmarket.daangntoyproject.post.domain.Region;
import daangnmarket.daangntoyproject.user.domain.User;
import daangnmarket.daangntoyproject.user.model.UserDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString
public class PostDetailDto {
    private int postId;
    private String postTitle;

    private String postContent;

    private Date createdAt;

    private Date updatedAt;

    private String status;  // O : 진행중 , R : 예약중, C : 거래완료

    private String price;

    private String proposalYn;

    private String categoryName;

    private String region;  // 어느 동인지만 알아도 됨

    private int viewCnt;

    private int likeCnt;

    private UserDto userDto;

    private List<String> imgUrl = new ArrayList<String>();

    public PostDetailDto(){}
    public PostDetailDto(Optional<Post> postEntity){
        this.postId = postEntity.get().getPostId();
        this.postTitle = postEntity.get().getPostTitle();
        this.postContent = postEntity.get().getPostContent();
        this.createdAt = postEntity.get().getCreatedAt();
        this.updatedAt = postEntity.get().getUpdatedAt();
        this.status = postEntity.get().getStatus();
        this.price = String.format("%,d", postEntity.get().getPrice());
        this.proposalYn = postEntity.get().getProposalYn();
        this.categoryName = postEntity.get().getCategory().getCategoryName();
        this.region = postEntity.get().getRegion().getSecDepth() + " " +postEntity.get().getRegion().getThirdDepth();
        this.viewCnt = postEntity.get().getViewCnt();
        this.likeCnt = postEntity.get().getLikeCnt();
        this.userDto = new UserDto(Optional.ofNullable(postEntity.get().getUser()));
        int idx = 0;
        for(Image image:postEntity.get().getImages()){
            imgUrl.add(idx, image.getImgUrl());
        }
    }

    //dto -> entity (db에 등록할 때 사용)
//    public Post toEntity(){
//        return Post.builder()
//                .postTitle(postTitle)
//                .postContent(postContent)
//                .createdAt(createdAt)
//                .updatedAt(updatedAt)
//                .status(status)
//                .price(Integer.parseInt(price.replaceAll(",", "")))
//                .proposalYn(proposalYn)
//                .viewCnt(viewCnt)
//                .likeCnt(likeCnt)
//                .build();
//    }

}
