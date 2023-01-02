package daangnmarket.daangntoyproject.chat.repository;

import daangnmarket.daangntoyproject.chat.domain.ChatContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatContentRepository extends JpaRepository<ChatContent, Integer> {
    List<ChatContent> findByUserIdAndRoomIdOrderByCreateDate(String loginId, int pId);
}
