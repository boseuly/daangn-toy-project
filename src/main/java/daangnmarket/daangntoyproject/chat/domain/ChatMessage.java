package daangnmarket.daangntoyproject.chat.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatMessage {
    private int roomId;
    private String message;
    private String userId;
}
