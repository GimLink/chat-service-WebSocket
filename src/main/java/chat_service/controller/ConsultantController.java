package chat_service.controller;

import chat_service.dto.MemberDto;
import chat_service.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/consultants")
@RequiredArgsConstructor
@Slf4j
public class ConsultantController {

  private final CustomUserDetailsService customUserDetailsService;

  @ResponseBody
  @PostMapping
  public MemberDto saveMember(@RequestBody MemberDto memberDto) {
    return customUserDetailsService.saveMember(memberDto);
  }

  @GetMapping
  public String index() {
    return "/consultants/index.html";
  }

}
