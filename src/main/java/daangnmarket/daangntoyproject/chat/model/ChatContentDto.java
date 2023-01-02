package daangnmarket.daangntoyproject.chat.model;

import daangnmarket.daangntoyproject.chat.domain.ChatContent;
import daangnmarket.daangntoyproject.user.model.UserDto;
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
    private String userId;  // 보낸 사람
    private String chatContent;
    private LocalDateTime createDate;
    private String checkYn;

    private UserDto UserDto;


    public ChatContentDto(ChatContent chatContent){
        this.chatId = chatContent.getChatId();
        this.roomId = chatContent.getRoomId();
        this.userId = chatContent.getUserId();
        this.chatContent = chatContent.getChatContent();
        this.createDate = chatContent.getCreateDate();
        this.checkYn = chatContent.getCheckYn();
    }

    public ChatContent toEntity(){
        return ChatContent.builder()
                .roomId(roomId)
                .userId(userId)
                .chatContent(chatContent)
                .createDate(createDate)
                .checkYn(checkYn)
                .build();
    }
}
