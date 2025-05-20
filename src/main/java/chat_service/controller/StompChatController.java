package chat_service.controller;

import chat_service.dto.ChatMessage;
import chat_service.entities.Message;
import chat_service.service.ChatService;
import chat_service.vos.CustomOAuth2User;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StompChatController {

  private final ChatService chatService;

  @MessageMapping("/chats/{chatRoomId}")
  @SendTo("/sub/chats/{chatRoomId}")
  public ChatMessage handleMessage(Principal principal,
      @DestinationVariable Long chatRoomId, @Payload Map<String, String> payload) {
    log.info("{} sent {} in {}", principal.getName(), payload, chatRoomId);

    CustomOAuth2User user = (CustomOAuth2User) ((OAuth2AuthenticationToken) principal).getPrincipal();

    Message message = chatService.saveMessage(user.getMember(), chatRoomId, payload.get("message"));

    return new ChatMessage(principal.getName(), payload.get("message"));
  }

}
