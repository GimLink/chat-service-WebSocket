package chat_service.vos;

import chat_service.entities.Member;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User {

  Member member;
  Map<String, Object> attributeMap;

  public Member getMember() {
    return this.member;
  }

  @Override
  public String getName() {
    return this.member.getName();
  }

  @Override
  public Map<String, Object> getAttributes() {
    return Map.of();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(() -> this.member.getRole());
  }
}
