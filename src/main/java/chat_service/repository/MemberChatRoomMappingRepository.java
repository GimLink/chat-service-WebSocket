package chat_service.repository;

import chat_service.entities.MemberChatRoomMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberChatRoomMappingRepository extends JpaRepository<MemberChatRoomMapping, Long> {

}
