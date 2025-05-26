package chat_service.dto;

import chat_service.entities.Member;
import chat_service.enums.Gender;
import java.time.LocalDate;

public record MemberDto(
    Long id,
    String email,
    String nickName,
    String name,
    String password,
    String confirmedPassword,
    Gender gender,
    String phoneNumber,
    LocalDate birthday,
    String role
) {

  public static MemberDto fromEntity(Member member) {
    return new MemberDto(
        member.getId(),
        member.getEmail(),
        member.getNickname(),
        member.getName(),
        null,
        null,
        member.getGender(),
        member.getPhoneNumber(),
        member.getBirthday(),
        member.getRole()
    );
  }

  public static Member to(MemberDto memberDto) {
    return Member.builder()
        .id(memberDto.id())
        .email(memberDto.email())
        .nickname(memberDto.nickName())
        .name(memberDto.name())
        .gender(memberDto.gender())
        .phoneNumber(memberDto.phoneNumber())
        .birthday(memberDto.birthday())
        .role(memberDto.role())
        .build();
  }

}
