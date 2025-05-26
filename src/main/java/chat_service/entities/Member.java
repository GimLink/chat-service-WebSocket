package chat_service.entities;

import chat_service.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
  @Id
  @Column(name = "member_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String email;
  String password;
  String nickname;
  String name;
  @Enumerated(EnumType.STRING)
  Gender gender;
  String phoneNumber;
  LocalDate birthday;
  String role;

  public void updatePassword(String password, String confirmedPassword, PasswordEncoder passwordEncoder) {
    if (!password.equals(confirmedPassword)){
      throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
    }

    this.password = passwordEncoder.encode(password);
  }
}
