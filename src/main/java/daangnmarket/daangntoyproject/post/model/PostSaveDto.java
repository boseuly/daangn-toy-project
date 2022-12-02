package daangnmarket.daangntoyproject.post.model;

import daangnmarket.daangntoyproject.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostSaveDto {
    // 게시글 저장할 때 필요한 객체
    private int postId;
    private String postTitle;

    private String postContent;

    private Date createdAt;

    private Date updatedAt;

    private String status;  // O : 진행중 , R : 예약중, C : 거래완료

    private String price;

    private String proposalYn;

    private int categoryId;     // ajax -> controller 받아올 때 사용

    private int regionId;

    private int viewCnt;

    private int likeCnt;

    private String userId;

    //dto -> entity (db에 등록할 때 사용)
    public Post toEntity(){
        return Post.builder()
                .postTitle(postTitle)
                .postContent(postContent)
                .price(Integer.parseInt(price.replaceAll(",", "")))
                .proposalYn(proposalYn)
                .createdAt(LocalDate.now())
                .updatedAt(updatedAt)
                .status("O")
                .deletedYn("N")
                .regionId(regionId)
                .categoryId(categoryId)
                .userId(userId)
                .viewCnt(viewCnt)
                .likeCnt(likeCnt)
                .build();
    }

    public PostSaveDto(Optional<Post> postEntity) {
        this.postId = postEntity.get().getPostId();
        this.postTitle = postEntity.get().getPostTitle();
        this.postContent = postEntity.get().getPostContent();
        this.createdAt = postEntity.get().getCreatedAt();
        this.updatedAt = postEntity.get().getUpdatedAt();
        this.status = postEntity.get().getStatus();
        this.price = String.format("%,d", postEntity.get().getPrice());
        this.proposalYn = postEntity.get().getProposalYn();
        this.categoryId = postEntity.get().getCategory().getCategoryId();
        this.regionId = postEntity.get().getRegion().getRegionId();
        this.viewCnt = postEntity.get().getViewCnt();
        this.likeCnt = postEntity.get().getLikeCnt();
        this.userId = postEntity.get().getUserId();
    }
}
