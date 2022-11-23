package daangnmarket.daangntoyproject.board.domain;

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

    @Column(name = "seller_id")
    private String sellerId;

    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "post_content")
    private String postContent;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    private String status;

    @Column(name = "deleted_yn")
    private String deletedYn;

    private int price;

    @Column(name = "price_proposal_yn")
    private String proposalYn;

    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "view_cnt")
    private int viewCnt;

    @Column(name = "like_cnt")
    private int likeCnt;

    @OneToMany(mappedBy="post",fetch = FetchType.LAZY)  // 필요할 때만 데이터를 가져올 수 있다.
    private List<Image> images = new ArrayList<Image>();

    public Post(){}

    // 테이블 생성 시 필요한 생성자
    public Post(String sellerId, String postTitle, String postContent, int price, String proposalYn, int categoryId) {
        this.sellerId = sellerId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.price = price;
        this.proposalYn = proposalYn;
        this.categoryId = categoryId;
    }

}
