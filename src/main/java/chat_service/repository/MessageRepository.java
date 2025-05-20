package chat_service.repository;

import chat_service.entities.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

  List<Message> findAllByChatRoomId(Long chatRoomId);

}
