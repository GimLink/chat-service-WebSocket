package chat_service.repository;

import chat_service.entities.MemberChatRoomMapping;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberChatRoomMappingRepository extends JpaRepository<MemberChatRoomMapping, Long> {

  public Boolean existsByMemberIdAndChatRoomId(Long memberId, Long chatRoomId);

  public void deleteByMemberIdAndChatRoomId(Long memberId, Long chatRoomId);

  public List<MemberChatRoomMapping> findAllByMemberId(Long memberId);

}
