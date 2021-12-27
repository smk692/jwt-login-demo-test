package kr.co.demo.son.demo.src.dto.login;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {

    private Long id;
    private String mobileNo;
    private String email;
    private String accessToken;
}
