package daangnmarket.daangntoyproject.chat.repository;

import daangnmarket.daangntoyproject.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    ChatRoom findByBuyerIdAndPostId(String buyerId, int pId);
}
