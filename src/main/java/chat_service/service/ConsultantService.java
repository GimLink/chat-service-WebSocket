package chat_service.service;

import chat_service.dto.ChatRoomDto;
import chat_service.dto.MemberDto;
import chat_service.entities.ChatRoom;
import chat_service.entities.Member;
import chat_service.enums.Role;
import chat_service.repository.ChatRoomRepository;
import chat_service.repository.MemberRepository;
import chat_service.vos.CustomUserdetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultantService implements UserDetailsService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final ChatRoomRepository chatRoomRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Member member = memberRepository.findByName(username).get();

    if (Role.fromCode(member.getRole()) != Role.CONSULTANT) {
      throw new AccessDeniedException("상담사가 아닙니다.");
    }
    return new CustomUserdetails(member, null);
  }

  public MemberDto saveMember(MemberDto memberDto) {
    Member member = MemberDto.to(memberDto);
    member.updatePassword(memberDto.password(), memberDto.confirmedPassword(), passwordEncoder);

    return MemberDto.fromEntity(memberRepository.save(member));
  }

  public Page<ChatRoomDto> getChatRoomPage(Pageable pageable){
    Page<ChatRoom> chatRoomPage = chatRoomRepository.findAll(pageable);

    return chatRoomPage.map(ChatRoomDto::fromEntity);
  }
}
