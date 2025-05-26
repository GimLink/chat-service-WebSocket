package chat_service.controller;

import chat_service.dto.ChatRoomDto;
import chat_service.dto.MemberDto;
import chat_service.service.ConsultantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/consultants")
@RequiredArgsConstructor
@Slf4j
public class ConsultantController {

  private final ConsultantService consultantService;

  @ResponseBody
  @PostMapping
  public MemberDto saveMember(@RequestBody MemberDto memberDto) {
    return consultantService.saveMember(memberDto);
  }

  @GetMapping
  public String index() {
    return "/consultants/index.html";
  }

  @ResponseBody
  @GetMapping("/chats")
  public Page<ChatRoomDto> getChatRoomPage(Pageable pageable){
    return consultantService.getChatRoomPage(pageable);
  }

}
