package daangnmarket.daangntoyproject.post.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String updatedAt;

    private String status;  // O : 진행중 , R : 예약중, C : 거래완료

    private String price;

    private String proposalYn;

    private String categoryName;


    private String region;

    private int viewCnt;

    private int likeCnt;

    private UserDto userDto;
    private String userId;

    private List<String> imgUrl = new ArrayList<String>();

    public void setImgUrl(List<Image> images){
        int idx = 0;
        for (Image image : images){
            imgUrl.add(idx, image.getImgUrl());
        }
    }
    public PostDetailDto(){}
    public PostDetailDto(Post postEntity){
        this.postId = postEntity.getPostId();
        this.postTitle = postEntity.getPostTitle();
        this.postContent = postEntity.getPostContent();
        this.createdAt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(postEntity.getCreatedAt());
        if(updatedAt != null){
            this.updatedAt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(postEntity.getUpdatedAt());
        }
        this.status = postEntity.getStatus();
        this.price = String.format("%,d", postEntity.getPrice());
        this.proposalYn = postEntity.getProposalYn();
        this.categoryName = postEntity.getCategory().getCategoryName();
        this.region = postEntity.getRegion().getSecDepth() + " " +postEntity.getRegion().getThirdDepth();
        this.viewCnt = postEntity.getViewCnt();
        this.likeCnt = postEntity.getLikeCnt();
        this.userId = postEntity.getUserId();
    }
    public PostDetailDto(Optional<Post> postEntity){
        this.postId = postEntity.get().getPostId();
        this.postTitle = postEntity.get().getPostTitle();
        this.postContent = postEntity.get().getPostContent();
        this.createdAt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(postEntity.get().getCreatedAt());
        if(postEntity.get().getUpdatedAt() != null){
            this.updatedAt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(postEntity.get().getUpdatedAt());
        }
        this.status = postEntity.get().getStatus();
        this.price = String.format("%,d", postEntity.get().getPrice());
        this.proposalYn = postEntity.get().getProposalYn();
        this.categoryName = postEntity.get().getCategory().getCategoryName();
        this.region = postEntity.get().getRegion().getSecDepth() + " " +postEntity.get().getRegion().getThirdDepth();
        this.viewCnt = postEntity.get().getViewCnt();
        this.likeCnt = postEntity.get().getLikeCnt();
        this.userId = postEntity.get().getUserId();
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
