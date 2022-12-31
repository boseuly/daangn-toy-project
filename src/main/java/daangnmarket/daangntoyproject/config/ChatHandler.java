package daangnmarket.daangntoyproject.config;

import daangnmarket.daangntoyproject.chat.service.ChatService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler { // text 기반의 채팅을 구현할 것 -> textwebsocketHandler 상속
    @Autowired
    private ChatService chatService;
    private static List<WebSocketSession> list = new ArrayList<>();


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("전달 메시지 내용 payload : " + payload);   // payload : 전송되는 데이터를 의미. JSON에서 페이로드는 data이다.

        for(WebSocketSession sess: list) {
            sess.sendMessage(message);
        }
    }

    /* Client가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        list.add(session);

        log.info(session + " 클라이언트 접속");
    }

    /* Client가 접속 해제 시 호출되는 메서드 */

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        log.info(session + " 클라이언트 접속 해제");
        list.remove(session);
    }
}
