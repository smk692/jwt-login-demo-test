package kr.co.demo.son.demo.src.dto;

import kr.co.demo.son.demo.src.enumType.EnumType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
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
    // 데이터 활성화 여부
    private EnumType.YNType activeYn;
    // 생성일
    private LocalDateTime createdAt;
    // 수정일
    private LocalDateTime updatedAt;
    // 생성자
    private Integer createdWorkerId;
    // 수정자
    private Integer updatedWorkerId;
}
