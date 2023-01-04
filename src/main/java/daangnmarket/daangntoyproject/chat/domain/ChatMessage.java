package daangnmarket.daangntoyproject.chat.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ChatMessage {
    private int roomId;
    private String message;
    private String userId;
    private String imgUrl;
    private boolean result; // db에 저장 성공 여부
}
