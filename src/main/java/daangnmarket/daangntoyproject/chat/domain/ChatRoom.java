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
    @Column(name = "chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatId;

    @NotNull
    @Column(name = "seller_id")
    private String sellerId;

    @NotNull
    @Column(name = "buyer_id")
    private String buyer_id;

    @NotNull
    @Column(name = "post_id")
    private int postId;

    @Column(name = "seller_delete_yn")
    private String sellerDeleteYn;

    @Column(name = "buyer_delete_yn")
    private String buyerDeleteYn;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Builder
    public ChatRoom(int chatId, String sellerId, String buyer_id, int postId
            , String sellerDeleteYn, String buyerDeleteYn, LocalDate createDate) {
        this.chatId = chatId;
        this.sellerId = sellerId;
        this.buyer_id = buyer_id;
        this.postId = postId;
        this.sellerDeleteYn = sellerDeleteYn;
        this.buyerDeleteYn = buyerDeleteYn;
        this.createDate = createDate;
    }

}
