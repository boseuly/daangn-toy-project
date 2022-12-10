package daangnmarket.daangntoyproject.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import daangnmarket.daangntoyproject.post.model.PostSaveDto;
import daangnmarket.daangntoyproject.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="tb_post")
@DynamicInsert
@DynamicUpdate
public class Post { // dynamicInsert와 dynamicUpdate는 null인 값은 제외하고 추가해준다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int postId;

    @Column(name = "seller_id")
    private String userId;

    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "post_content")
    private String postContent;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private String status;  // O : 진행중 , R : 예약중, C : 거래완료

    @Column(name = "deleted_yn")
    private String deletedYn;

    private int price;

    @Column(name = "price_proposal_yn")
    private String proposalYn;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    @JsonIgnore
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.DETACH)
    @JoinColumn(name = "region_id", referencedColumnName = "region_id")
    @JsonIgnore
    private Region region;

    @Column(name = "view_cnt")
    private int viewCnt;

    @Column(name = "like_cnt")
    private int likeCnt;



    // 원래 Entity에는 Setter만들면 안 되는데 불가피했음 -> 데이터 저장할 때 사용
    public void setRegion(int regionId){
        this.region = new Region(regionId);
    }
    public void setCategory(int categoryId){
        this.category = new Category(categoryId);
    }

    public void setViewCnt(int viewCnt){
        this.viewCnt = viewCnt;
    }

    public Post(){}

    @Builder
    public Post(int PostId, String postTitle, String postContent,
                int price, String proposalYn, int regionId, int categoryId, String userId,
                LocalDateTime createdAt, LocalDateTime updatedAt, String status, String deletedYn,
                int viewCnt, int likeCnt) {
        // 구현하기
        if(postId != 0){
            this.postId = postId;
        }
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.price = price;
        this.proposalYn = proposalYn;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.deletedYn = deletedYn;
        this.viewCnt = viewCnt;
        this.likeCnt = likeCnt;
        this.userId = userId;
        this.setRegion(regionId);
        this.setCategory(categoryId);

    }
    public void update(PostSaveDto saveDto) {
        this.postTitle = saveDto.getPostTitle();
        this.postContent = saveDto.getPostContent();
        this.price = Integer.parseInt(saveDto.getPrice().replaceAll(",", ""));
        this.proposalYn = saveDto.getProposalYn();
        this.updatedAt = LocalDateTime.now();
        this.setRegion(saveDto.getRegionId());
        this.setCategory(saveDto.getCategoryId());
    }

}
