package kr.co.demo.son.demo.src.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveMemberDto {
    // 이메일
    private String email;
    // 이름
    private String name;
    // 닉네임
    private String nickname;
    // 비밀번호
    private String password;
    // 핸드폰 번호
    private String mobileNo;
}
