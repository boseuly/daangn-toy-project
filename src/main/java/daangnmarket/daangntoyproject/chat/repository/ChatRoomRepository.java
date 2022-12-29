package daangnmarket.daangntoyproject.chat.repository;

import daangnmarket.daangntoyproject.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    ChatRoom findByBuyerIdAndPostId(String buyerId, int pId);

    List<ChatRoom> findByBuyerIdOrSellerId(String buyerId, String buyerId1);


    @Query(value = "select r from ChatRoom r " +
            "where (r.buyerId = :buyerId or r.sellerId = :sellerId) and r.postId = :pId")
    ChatRoom findByBuyerIdOrSellerIdAndPostId(String buyerId,String sellerId, int pId); // loginId, sellerId 둘다 loginId임
}
