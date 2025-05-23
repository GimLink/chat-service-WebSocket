package chat_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MemberChatRoomMapping {

  @Id
  @Column(name = "member_chatroom_mapping_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  Member member;

  @ManyToOne
  @JoinColumn(name = "chatroom_id")
  ChatRoom chatRoom;

  LocalDateTime lastCheckedAt;

  public void updateLastCheckedAt() {
    this.lastCheckedAt = LocalDateTime.now();
  }

}
