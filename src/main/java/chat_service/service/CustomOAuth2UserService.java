package chat_service.service;

import chat_service.entities.Member;
import chat_service.enums.Gender;
import chat_service.repository.MemberRepository;
import chat_service.vos.CustomOAuth2User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final MemberRepository memberRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);
    if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
      Map<String, Object> attributeMap = oAuth2User.getAttributes();
      String email = (String) attributeMap.get("email");
      Member member = memberRepository.findByEmail(email)
          .orElseGet(() -> {
            Member newMember = MemberFactory.createMember(userRequest, oAuth2User);
            return memberRepository.save(newMember);
          });
      return new CustomOAuth2User(member, oAuth2User.getAttributes());
    } else {
      String email = oAuth2User.getAttribute("email");
      Member member = memberRepository.findByEmail(email)
          .orElseGet(() -> {
            Member newMember = MemberFactory.createMember(userRequest, oAuth2User);
            return memberRepository.save(newMember);
          });
      return new CustomOAuth2User(member, oAuth2User.getAttributes());
    }
  }
}
