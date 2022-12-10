package daangnmarket.daangntoyproject.post.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import daangnmarket.daangntoyproject.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String updatedAt;

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
//                .createdAt(String.valueOf(LocalDateTime.parse(createdAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
                .createdAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()))
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
        this.createdAt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(postEntity.get().getCreatedAt());
        this.updatedAt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(postEntity.get().getUpdatedAt());
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
