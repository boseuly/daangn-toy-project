package daangnmarket.daangntoyproject.board.domain;

import daangnmarket.daangntoyproject.board.model.PostListDto;
import daangnmarket.daangntoyproject.user.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="tb_post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int postId;

    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "post_content")
    private String postContent;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    private String status;  // O : 진행중 , R : 예약중, C : 거래완료

    @Column(name = "deleted_yn")
    private String deletedYn;

    private int price;

    @Column(name = "price_proposal_yn")
    private String proposalYn;

    @Column(name = "category_id")
    private int categoryId;

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "region_id")
    private Region region;

    @Column(name = "view_cnt")
    private int viewCnt;

    @Column(name = "like_cnt")
    private int likeCnt;

    @OneToMany(mappedBy="post",fetch = FetchType.LAZY)  // 필요할 때만 데이터를 가져올 수 있다.
    private List<Image> images = new ArrayList<Image>();


    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "user_id")
    private User user;

    // 원래 Entity에는 Setter만들면 안 되는데 불가피했음 -> 데이터 저장할 때 사용
    public void setUser(User user){
        this.user = user;
    }
    public void setRegion(Region region){
        this.region = region;
    }

    public Post(){}

    // 데이터 DB에 추가 시 사용
    public Post(String postTitle, String postContent, int price, String proposalYn, int categoryId) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.price = price;
        this.proposalYn = proposalYn;
        this.categoryId = categoryId;
    }

}
