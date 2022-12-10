package daangnmarket.daangntoyproject.post.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.time.format.DateTimeFormatter;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
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
                String createdAt, String updatedAt, String status, String deletedYn,
                int viewCnt, int likeCnt) {
        if(postId != 0){
            this.postId = postId;
        }
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.price = price;
        this.proposalYn = proposalYn;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");   // 파싱 오류 때문에 형식 지정
        this.createdAt = LocalDateTime.parse(createdAt, formatter);
        this.updatedAt = LocalDateTime.parse(updatedAt,formatter);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");   // 파싱 오류 때문에 형식 지정
        this.updatedAt = LocalDateTime.parse(saveDto.getUpdatedAt(),formatter);
        this.setRegion(saveDto.getRegionId());
        this.setCategory(saveDto.getCategoryId());
    }

}
