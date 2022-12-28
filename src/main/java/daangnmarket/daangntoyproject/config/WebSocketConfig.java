package daangnmarket.daangntoyproject.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final ChatHandler chatHandler;
    @Override
    // connection을 맺을때 CORS 허용
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "ws/chat")
                .setAllowedOriginPatterns("http://*:8080", "http://*.*.*.*:8080");   // 보안상의 문제로 "*"(모두허용)보다는 하나씩 지정해주는 것이 좋다.
//                .setAllowedOrigins("*");
//                .withSockJS()                                                       // javaConfig를 통해서 sockJS를 사용할 수 있도록 설정함
//                .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.2/sockjs.js");
                //.setClientLibarayUrl은 그냥 sockjs CDN 주소를 입력해도 무관하다.
                //https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.2/sockjs.js

                /*
                Spring Boot에서 CORS 설정 시, .allowCredentials(true)와
                .allowedOrigins("*")는 동시 설정을 못하도록 업데이트 되었다고 한다.
                모든 주소를 허용하는 대신 특정 7패턴만 허용하는 것으로 적용해야한다고 변동됨.

                .allowedOrigins("*") 대신 .allowedOriginPatterns("*")를 사용하면 에러는 해결이
                된다고 한다.

                나는 이처럼 하지 않고, http://localhost:8080 또는, IP 주소로 접속하기 때문에
                위에 설정처럼 하였다.
                */
    }
}
