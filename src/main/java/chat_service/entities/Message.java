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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Message {

  @Column(name = "message_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  Long id;

  String text;

  @JoinColumn(name = "member_id")
  @ManyToOne
  Member member;

  @ManyToOne
  @JoinColumn(name = "chatroom_id")
  ChatRoom chatRoom;

  LocalDateTime createdAt;

}
