package daangnmarket.daangntoyproject.chat.model;

import daangnmarket.daangntoyproject.chat.domain.ChatRoom;
import daangnmarket.daangntoyproject.user.model.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatRoomDto {
    private int roomId;
    private String sellerId;
    private String buyerId;
    private int postId;
    private String sellerDeleteYn;
    private String buyerDeleteYn;

    private UserDto sellerUserDto;
    private UserDto buyerUserDto;

    public ChatRoomDto(ChatRoom chatRoom){
        this.roomId =  chatRoom.getRoomId();
        this.sellerId = chatRoom.getSellerId();
        this.buyerId = chatRoom.getBuyerId();
        this.postId = chatRoom.getPostId();
    }

    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .sellerId(sellerId)
                .buyerId(buyerId)
                .postId(postId)
                .sellerDeleteYn(sellerDeleteYn)
                .buyerDeleteYn(buyerDeleteYn)
                .build();
    }

}
