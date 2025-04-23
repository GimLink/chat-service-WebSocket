package chat_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ChatRoom {

  @Id
  @Column(name = "chatroom_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String title;

  LocalDateTime createdAt;

  @OneToMany(mappedBy = "chatRoom")
  Set<MemberChatRoomMapping> memberChatRoomMappingsSet;

}
