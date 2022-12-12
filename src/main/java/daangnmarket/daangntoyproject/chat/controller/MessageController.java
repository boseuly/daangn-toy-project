package daangnmarket.daangntoyproject.chat.controller;

import org.springframework.stereotype.Controller;

import javax.websocket.server.ServerEndpoint;
import java.net.Socket;

@Controller
@ServerEndpoint("/websocket")
public class MessageController extends Socket {

}
