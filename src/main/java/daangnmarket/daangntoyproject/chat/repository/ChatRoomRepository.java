package daangnmarket.daangntoyproject.chat.repository;

import daangnmarket.daangntoyproject.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    ChatRoom findByBuyerIdAndPostId(String buyerId, int pId);

    List<ChatRoom> findByBuyerIdOrSellerId(String buyerId, String buyerId1);
}
