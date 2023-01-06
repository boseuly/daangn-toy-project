package daangnmarket.daangntoyproject.chat.controller;

import daangnmarket.daangntoyproject.chat.model.ChatContentDto;
import daangnmarket.daangntoyproject.chat.model.ChatRoomDto;
import daangnmarket.daangntoyproject.chat.service.ChatService;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Log4j2
public class ChatController {
    /*
    https://retrieverj.tistory.com/23 : db 저장
    https://dalili.tistory.com/125 : 채팅방 구분
    https://velog.io/@ihj0043/WebSocket-%EC%B1%84%ED%8C%85%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%A8-%EB%8B%A8%EC%88%9C-%EC%B1%84%ED%8C%85
    https://velog.io/@ihj0043/WebSocket-%EC%B1%84%ED%8C%85%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%A8-%EC%B1%84%ED%8C%85%EB%B0%A9-%EB%A7%8C%EB%93%A4%EA%B8%B0-2
     */

    private static final Logger logger =  LoggerFactory.getLogger(ChatController.class);
    @Autowired
    private ChatService chatService;

    @GetMapping(value = "/chat")
    public String chat (@RequestParam(value = "loginId") String loginId,                // sellerId는 postId를 통해서 알 수 있음
                        @RequestParam(value = "pId", required = false) String postId,   // 채팅 목록만 나오도록 하는 경우
                        Model model){
        logger.info("chatController(buyerId={}, postId={})", loginId, postId);

        if(postId != null){
            List<ChatContentDto> chatContentDtos = chatService.findChatContents(loginId, Integer.parseInt(postId));
            model.addAttribute("postId", Integer.parseInt(postId));
            model.addAttribute("chatContents", chatContentDtos);
        }

        List<ChatRoomDto> chatRoomDtos = chatService.findChatRooms(loginId);
        model.addAttribute("chatRooms", chatRoomDtos);
        return "/chat/chat-content";
    }

}
