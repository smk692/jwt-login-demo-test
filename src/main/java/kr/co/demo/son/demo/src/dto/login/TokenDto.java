package kr.co.demo.son.demo.src.dto.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
    private Long id;
    private String email;
    private String mobileNo;

    public TokenDto(Long id, String email, String mobileNo) {
        this.id = id;
        this.email = email;
        this.mobileNo = mobileNo;
    }
}
