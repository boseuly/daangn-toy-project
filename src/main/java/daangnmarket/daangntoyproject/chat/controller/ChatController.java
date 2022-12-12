package daangnmarket.daangntoyproject.chat.controller;

import daangnmarket.daangntoyproject.chat.model.ChatContentDto;
import daangnmarket.daangntoyproject.chat.model.ChatRoomDto;
import daangnmarket.daangntoyproject.chat.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ChatController {

    private static final Logger logger =  LoggerFactory.getLogger(ChatController.class);
    @Autowired
    private ChatService chatService;

    @GetMapping(value = "/chat")
    public String chat (@RequestParam(value = "bId") String buyerId,
                        @RequestParam(value = "pId") int pId,
                        Model model){
        logger.info("chatController(buyerId={}, postId={})", buyerId, pId);
        List<ChatContentDto> chatContentDtos = chatService.findChatContents(buyerId, pId);
        List<ChatRoomDto> chatRoomDtos = chatService.findChatRooms(buyerId);
        logger.info("controller - chatContentDtos={}, chatRoomDtos={}", chatContentDtos, chatRoomDtos);

        model.addAttribute("chatContents", chatContentDtos);
        model.addAttribute("chatRooms", chatRoomDtos);
        return "/chat/chat-content";
    }

}
