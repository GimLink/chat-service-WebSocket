package chat_service.vos;

import chat_service.entities.Member;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserdetails extends CustomOAuth2User implements UserDetails {

  public CustomUserdetails(Member member, Map<String, Object> attributeMap) {
    super(member, attributeMap);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(this.member.getRole()));
  }

  @Override
  public String getPassword() {
    return this.member.getPassword();
  }

  @Override
  public String getUsername() {
    return this.member.getName();
  }
}

