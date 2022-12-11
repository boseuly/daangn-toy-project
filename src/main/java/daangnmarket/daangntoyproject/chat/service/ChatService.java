package daangnmarket.daangntoyproject.chat.service;

import daangnmarket.daangntoyproject.chat.controller.ChatController;
import daangnmarket.daangntoyproject.chat.domain.ChatContent;
import daangnmarket.daangntoyproject.chat.domain.ChatRoom;
import daangnmarket.daangntoyproject.chat.model.ChatContentDto;
import daangnmarket.daangntoyproject.chat.model.ChatRoomDto;
import daangnmarket.daangntoyproject.chat.repository.ChatContentRepository;
import daangnmarket.daangntoyproject.chat.repository.ChatRoomRepository;
import daangnmarket.daangntoyproject.post.domain.Post;
import daangnmarket.daangntoyproject.post.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    private static final Logger logger =  LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private ChatContentRepository chatContentRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private PostRepository postRepository;

    public List<ChatContentDto> findChatContents(String buyerId , int pId) {
        logger.info("findChatContents - service(buyerId={}, pId={})", buyerId, pId);
        // 만약 buyerId 와 pId에 해당하는 roomId가 없다면 room을 생성해준다.
        ChatRoomDto chatRoomDto = null;
        ChatRoom chatRoom = chatRoomRepository.findByBuyerIdAndPostId(buyerId, pId);
        if(chatRoom == null){
            Post postEntity = postRepository.findById(pId).orElse(null);   // sellerId 찾기
            chatRoom = new ChatRoom(postEntity.getUserId(),buyerId, pId);       // 새로운 방을 만들어준 뒤
            chatRoom =  chatRoomRepository.save(chatRoom);                      // entity 다시 받아옴
            chatRoomDto = new ChatRoomDto(chatRoom);
        }else {
            chatRoomDto = new ChatRoomDto(chatRoom);
        }

        List<ChatContentDto> chatContentDtos = new ArrayList<ChatContentDto>();
        List<ChatContent> chatContents = chatContentRepository.findByBuyerIdAndRoomIdOrderByCreateDate(buyerId, chatRoomDto.getRoomId());
        int idx = 0;
        for (ChatContent content:chatContents){
            chatContentDtos.add(idx, new ChatContentDto(content));
            idx++;
        }
        return chatContentDtos;
    }

    // login 유저의 chatRooms 찾기
    public List<ChatRoomDto> findChatRooms(String buyerId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findByBuyerIdOrSellerId(buyerId, buyerId);
        int idx = 0;
        List<ChatRoomDto> chatRoomDtos = new ArrayList<ChatRoomDto>();
        for (ChatRoom chatRoom : chatRooms){
            chatRoomDtos.add(idx, new ChatRoomDto(chatRoom));
            idx++;
        }

        return chatRoomDtos;
    }
}
