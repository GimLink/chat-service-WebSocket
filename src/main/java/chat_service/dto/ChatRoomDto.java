package chat_service.dto;

import chat_service.entities.ChatRoom;
import java.time.LocalDateTime;

public record ChatRoomDto(
    Long id,
    String title,
    Boolean hasNewMessage,
    Integer memberCount,
    LocalDateTime createdAt) {

  public static ChatRoomDto fromEntity(ChatRoom chatRoom) {
    return new ChatRoomDto(chatRoom.getId(), chatRoom.getTitle(), chatRoom.getHasNewMessage(), chatRoom.getMemberChatRoomMappingsSet().size(), chatRoom.getCreatedAt());
  }

}
