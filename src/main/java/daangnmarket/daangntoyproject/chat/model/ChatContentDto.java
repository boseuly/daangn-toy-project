package daangnmarket.daangntoyproject.chat.model;

import daangnmarket.daangntoyproject.chat.domain.ChatContent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatContentDto {
    private int chatId;
    private int roomId;
    private String sellerId;  // 보낸 사람
    private String buyerId;    // 받은 사람
    private String chatContent;
    private LocalDateTime createDate;
    private String checkYn;

    public ChatContentDto(ChatContent chatContent){
        this.chatId = chatContent.getChatId();
        this.roomId = chatContent.getRoomId();
        this.sellerId = chatContent.getSellerId();
        this.buyerId = chatContent.getBuyerId();
        this.chatContent = chatContent.getChatContent();
        this.createDate = chatContent.getCreateDate();
        this.checkYn = chatContent.getCheckYn();
    }

    public ChatContent toEntity(){
        return ChatContent.builder()
                .roomId(roomId)
                .sellerId(sellerId)
                .buyerId(buyerId)
                .chatContent(chatContent)
                .createDate(createDate)
                .checkYn(checkYn)
                .build();
    }
}
