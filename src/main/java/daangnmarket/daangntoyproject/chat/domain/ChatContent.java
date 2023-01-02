package daangnmarket.daangntoyproject.chat.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "tb_chat_content")
public class ChatContent {
    @Id
    @Column(name = "chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatId;

    @Column(name = "room_id")
    private int roomId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "chat_content")
    private String chatContent;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "check_yn")
    private String checkYn;

    @Column(name = "delete_yn")
    private String deleteYn;

    @Builder
    public ChatContent(int roomId, String userId, String chatContent
            , LocalDateTime createDate, String checkYn) {
        this.roomId = roomId;
        this.userId = userId;
        this.chatContent = chatContent;
        this.createDate = createDate;
        this.checkYn = checkYn;
    }
}
