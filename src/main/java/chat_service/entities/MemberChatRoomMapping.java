package chat_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
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

}
