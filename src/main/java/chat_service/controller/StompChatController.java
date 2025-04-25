package chat_service.controller;

import chat_service.dto.ChatMessage;
import java.security.Principal;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class StompChatController {

  @MessageMapping("/chats/{chatRoomId}")
  @SendTo("/sub/chats/{chatRoomId}")
  public ChatMessage handleMessage(Principal principal,
      @DestinationVariable Long chatRoomId, @Payload Map<String, String> payload) {
    log.info("{} sent {} in {}", principal.getName(), payload, chatRoomId);

    return new ChatMessage(principal.getName(), payload.get("message"));
  }

}
