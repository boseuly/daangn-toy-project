package daangnmarket.daangntoyproject.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import daangnmarket.daangntoyproject.chat.domain.ChatMessage;
import daangnmarket.daangntoyproject.chat.repository.ChatContentRepository;
import daangnmarket.daangntoyproject.chat.service.ChatService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler { // text 기반의 채팅을 구현할 것 -> textwebsocketHandler 상속
    @Autowired
    private ChatService chatService;
    private static Map<String, WebSocketSession> sessionMap = new HashMap<>(); // 여기 저장된 사용자들에게만 메시지를 전달한다.
    private final ObjectMapper objectMapper = new ObjectMapper();   // json -> object

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = message.getPayload();

        // Json 객체 -> Java 객체
        ChatMessage chatMessage = objectMapper.readValue(msg, ChatMessage.class);
        log.info("chatMessage 객체 : " + chatMessage);

        // 채팅 메시지 DB 저장
        chatService.saveChatContent(chatMessage);
        log.info("DB 저장 후 chatMessage : " + chatMessage);

        TextMessage textMessage = new TextMessage(chatMessage.getRoomId() + "," + chatMessage.getMessage() + "," + chatMessage.getUserId() + "," + chatMessage.getImgUrl());
        for(String key: sessionMap.keySet()) {
            WebSocketSession wss = sessionMap.get(key);
            try{
                wss.sendMessage(textMessage);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /* Client가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        // 소켓 연결
        super.afterConnectionEstablished(session);
        sessionMap.put(session.getId(), session);

        log.info(session + " 클라이언트 접속 -> session 추가");
    }

    /* Client가 접속 해제 시 호출되는 메서드 */

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        log.info(session + " 클라이언트 접속 해제 -> session 제거");
        sessionMap.remove(session.getId()); // 키를 사용해서 제거해준다.
        super.afterConnectionClosed(session, status);
    }
}
