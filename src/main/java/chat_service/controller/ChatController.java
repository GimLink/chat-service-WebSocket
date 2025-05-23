package chat_service.controller;

import chat_service.dto.ChatMessage;
import chat_service.dto.ChatRoomDto;
import chat_service.entities.ChatRoom;
import chat_service.entities.Message;
import chat_service.service.ChatService;
import chat_service.vos.CustomOAuth2User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

  private final ChatService chatService;

  @PostMapping
  public ChatRoomDto createChatRoom(@AuthenticationPrincipal CustomOAuth2User user,
      @RequestParam String title) {
    return ChatRoomDto.fromEntity(chatService.createChatRoom(user.getMember(), title));
  }

  @PostMapping("/{chatRoomId}")
  public Boolean joinChatRoom(@AuthenticationPrincipal CustomOAuth2User user,
      @PathVariable Long chatRoomId, @RequestParam(required = false) Long currentChatRoomId) {
    return chatService.joinChatRoom(user.getMember(), chatRoomId, currentChatRoomId);
  }

  @DeleteMapping("/{chatRoomId}")
  public Boolean leaveChatRoom(@AuthenticationPrincipal CustomOAuth2User user, @PathVariable Long chatRoomId) {
    return chatService.leaveChatRoom(user.getMember(), chatRoomId);
  }

  @GetMapping
  public List<ChatRoomDto> getChatRoomList(@AuthenticationPrincipal CustomOAuth2User user) {
    List<ChatRoom> chatRoomList = chatService.getChatRoomList(user.getMember());
    return chatRoomList.stream().map(ChatRoomDto::fromEntity).toList();
  }

  @GetMapping("/{chatRoomId}/messages")
  public List<ChatMessage> getMessageList(@PathVariable Long chatRoomId) {
    List<Message> messageList = chatService.getMessagesList(chatRoomId);
    return messageList.stream().map(message -> new ChatMessage(message.getMember().getNickname(), message.getText()))
        .toList();

  }

}
