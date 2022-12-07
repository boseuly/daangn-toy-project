package daangnmarket.daangntoyproject.chat.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "tb_chat_room")
public class ChatRoom {
    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;

    @NotNull
    @Column(name = "seller_id")
    private String sellerId;

    @NotNull
    @Column(name = "buyer_id")
    private String buyerId;

    @NotNull
    @Column(name = "post_id")
    private int postId;

    @Column(name = "seller_delete_yn")
    private String sellerDeleteYn;

    @Column(name = "buyer_delete_yn")
    private String buyerDeleteYn;

    @Column(name = "create_date")
    private LocalDate createDate;

    public ChatRoom(String sellerId, String buyerId, int postId) {
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.postId = postId;
    }

    // 수정 시 필요
    @Builder
    public ChatRoom(String sellerId, String buyerId, int postId, String sellerDeleteYn, String buyerDeleteYn) {
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.postId = postId;
        this.sellerDeleteYn = sellerDeleteYn;
        this.buyerDeleteYn = buyerDeleteYn;
    }

}
