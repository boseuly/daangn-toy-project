package daangnmarket.daangntoyproject.chat.model;

import daangnmarket.daangntoyproject.chat.domain.ChatRoom;
import daangnmarket.daangntoyproject.post.model.ImageDto;
import daangnmarket.daangntoyproject.post.model.PostListDto;
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
    private String prodImgUrl;    // 제품 관련된 사진을 보여줄 때 필요 postId와 맞는 imageUrl을 찾아서 대표 url을 저장을 한다.

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
