package chat_service.service;

import chat_service.entities.ChatRoom;
import chat_service.entities.Member;
import chat_service.entities.MemberChatRoomMapping;
import chat_service.entities.Message;
import chat_service.repository.ChatRoomRepository;
import chat_service.repository.MemberChatRoomMappingRepository;
import chat_service.repository.MessageRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

  private final ChatRoomRepository chatRoomRepository;
  private final MemberChatRoomMappingRepository memberChatRoomMappingRepository;
  private final MessageRepository messageRepository;

  public ChatRoom createChatRoom(Member member, String title) {
    ChatRoom chatRoom = ChatRoom.builder()
        .title(title)
        .createdAt(LocalDateTime.now())
        .build();

    chatRoom = chatRoomRepository.save(chatRoom);
    MemberChatRoomMapping memberChatRoomMapping = chatRoom.addMember(member);

    memberChatRoomMappingRepository.save(memberChatRoomMapping);

    return chatRoom;
  }

  public Boolean joinChatRoom(Member member, Long chatRoomId) {
    if (memberChatRoomMappingRepository.existsByMemberIdAndChatRoomId(member.getId(), chatRoomId)) {
      log.info("{} already joined chat room {}", member.getName(), chatRoomId);
      return false;
    }
    ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

    MemberChatRoomMapping memberChatRoomMapping = MemberChatRoomMapping.builder()
        .member(member)
        .chatRoom(chatRoom)
        .build();

    memberChatRoomMappingRepository.save(memberChatRoomMapping);

    return true;
  }

  @Transactional
  public Boolean leaveChatRoom(Member member, Long chatRoomId) {
    if (!memberChatRoomMappingRepository.existsByMemberIdAndChatRoomId(member.getId(),
        chatRoomId)) {
      log.info("{} is not in chat room {}", member.getName(), chatRoomId);
      return false;
    }

    memberChatRoomMappingRepository.deleteByMemberIdAndChatRoomId(member.getId(), chatRoomId);

    return true;
  }

  public List<ChatRoom> getChatRoomList(Member member) {
    List<MemberChatRoomMapping> memberChatRoomMappingList = memberChatRoomMappingRepository.findAllByMemberId(
        member.getId());

    return memberChatRoomMappingList.stream().map(MemberChatRoomMapping::getChatRoom).toList();
  }

  public Message saveMessage(Member member, Long chatRoomId, String text){
    ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();

    Message message = Message.builder()
        .text(text)
        .member(member)
        .chatRoom(chatRoom)
        .build();

    return messageRepository.save(message);
  }

  public List<Message> getMessagesList(Long chatRoomId){
    return messageRepository.findAllByChatRoomId(chatRoomId);
  }

}
