package app.project.InsuranceService.handler;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MyHandler extends TextWebSocketHandler {

    @Getter
    List<WebSocketSession> list = new ArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Test message {}", message.toString());

        list.add(session);
        session.sendMessage(new TextMessage(message.getPayload()));
        Thread.sleep(1000);
    }
}
