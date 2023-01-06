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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler { // text 기반의 채팅을 구현할 것 -> textwebsocketHandler 상속
    @Autowired
    private ChatService chatService;
    private static List<HashMap<String, Object>> roomSessionList = new ArrayList<>(); // 웹소켓 세션을 담아둘 리스트 - 여기 저장된 사용자들에게만 메시지를 전달한다.(room과 session저장)
    private final ObjectMapper objectMapper = new ObjectMapper();   // json -> object

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = message.getPayload();

        // Json 객체 -> Java 객체
        ChatMessage chatMessage = objectMapper.readValue(msg, ChatMessage.class);
        log.info("chatMessage 객체 : " + chatMessage);
        String rn = String.valueOf(chatMessage.getRoomId()); // chat 메시지가 들어왔을 때 roomId가 어디인지

        HashMap<String, Object> temp = new HashMap<String, Object>();
        if(roomSessionList.size() > 0){
            for(int i =0; i<roomSessionList.size(); i++){
                String roomNumber = (String) roomSessionList.get(i).get("roomNumber");  // session리스트의 저장된 방번호를 가져와서
                if(roomNumber.equals(rn)){    // 같은 값의 방이 존재한다면
                    temp = roomSessionList.get(i);  // 해당 방번호의 session리스트의 존재하는 모든 object 값을 가져온다.
                    break;
                }
            }

            // 채팅 메시지 DB 저장
            chatService.saveChatContent(chatMessage);
            log.info("DB 저장 후 chatMessage : " + chatMessage);

            // 전달 내용
            TextMessage textMessage = new TextMessage(chatMessage.getRoomId() + "," + chatMessage.getMessage() + "," + chatMessage.getUserId() + "," + chatMessage.getImgUrl());

            // 해당 방의 세션들만 찾아서 메시지를 발송해준다.
            for(String k:temp.keySet()){
                if(k.equals("roomNumber")){ // key값이 roomNumber와 같다면
                    continue;
                }

                WebSocketSession wss = (WebSocketSession) temp.get(k);
                if(wss != null){
                    try {
                        wss.sendMessage(textMessage);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /* Client가 접속 시 호출되는 메서드 */
    @SuppressWarnings("unchecked")
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        // 소켓 연결
        super.afterConnectionEstablished(session);
        boolean flag = false;      // 방이 이미 존재하는지 확인용
        String url = session.getUri().toString();
        log.info("소켓 연결 sessionUrl : " + url);

        String roomNumber = url.split("/chat/")[1]; // script에서 방번호 넘겨줌
        int idx = roomSessionList.size();
        if(roomSessionList.size() > 0){
            for(int i =0; i < roomSessionList.size(); i++){
                String rN =(String)roomSessionList.get(i).get("roomNumber");
                if(rN.equals(roomNumber)){
                    flag = true;    // 해당 room이 이미 존재한다.
                    idx = i;        // 몇 번 인덱스에 존재하는지 저장
                    break;
                }
            }
        }
        if(flag){   // 만약 방이 존재한다면 session만 추가
            HashMap<String, Object> map = roomSessionList.get(idx);
            map.put(session.getId(), session);
        }else {     // 방이 존재하지 않는다면 방번호와 session 추가
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("roomNumber", roomNumber);  // 방번호 추가
            map.put(session.getId(), session);  // session 추가
            roomSessionList.add(map);
        }

        // session 등록이 끝나면 발급 받은 sessionId 값의 메시지를 발송한다.

        log.info(session + " 클라이언트 접속 -> session 추가");
    }

    /* Client가 접속 해제 시 호출되는 메서드 */

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 소켓 종료
        if(roomSessionList.size() > 0){ // 소켓이 종료되면 해당 세션값들을 찾아서 지운다.
            for(int i=0; i< roomSessionList.size(); i++){
                roomSessionList.get(i).remove(session.getId());
            }
        }
        super.afterConnectionClosed(session, status);
        log.info(session + " 클라이언트 접속 해제 -> session 제거 완료");
    }
}
