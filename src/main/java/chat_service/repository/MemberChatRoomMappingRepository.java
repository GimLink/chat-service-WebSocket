package chat_service.repository;

import chat_service.entities.MemberChatRoomMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberChatRoomMappingRepository extends JpaRepository<MemberChatRoomMapping, Long> {

  Boolean existsByMemberIdAndChatRoomId(Long memberId, Long chatRoomId);

  void deleteByMemberIdAndChatRoomId(Long memberId, Long chatRoomId);

  List<MemberChatRoomMapping> findAllByMemberId(Long memberId);

  Optional<MemberChatRoomMapping> findByMemberIdAndChatRoomId(Long memberId, Long chatRoomId);

}
