package kr.co.demo.son.demo.src.dto.member;

import kr.co.demo.son.demo.src.enumType.EnumType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberMgtDto {
    private Long id;
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
    // 핸드폰 인증 여부
    private EnumType.MobileCertificationYn mobileCertificationYn;
}
