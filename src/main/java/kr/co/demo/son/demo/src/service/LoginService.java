package kr.co.demo.son.demo.src.service;

import kr.co.demo.son.demo.src.domain.Member.Member;
import kr.co.demo.son.demo.src.domain.Member.MemberRepository;
import kr.co.demo.son.demo.src.dto.login.LoginDto;
import kr.co.demo.son.demo.src.dto.login.LoginResponseDto;
import kr.co.demo.son.demo.src.dto.login.TokenDto;
import kr.co.demo.son.demo.src.dto.member.MemberPasswordDto;
import kr.co.demo.son.demo.src.enumType.EnumType;
import kr.co.demo.son.demo.src.system.filter.token.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final JwtTokenManager jwtTokenManager;

    public LoginResponseDto getLogin(LoginDto dto) {
        Optional<Member> user = memberRepository.findByMobileNoAndActiveYn(dto.getMobileNo(), EnumType.YNType.Y);

        if(!user.isPresent()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        Member userInfo = user.get();

        if (!BCrypt.checkpw(dto.getPassword(), userInfo.getPassword())) {
            throw new UsernameNotFoundException("비밀번호가 일치하지 않습니다.");
        }

        TokenDto tokenDto = new TokenDto(userInfo.getId(), userInfo.getEmail(), userInfo.getMobileNo());
        String accessToken = jwtTokenManager.generateToken(tokenDto);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .id(userInfo.getId())
                .mobileNo(userInfo.getMobileNo())
                .email(userInfo.getEmail())
                .build();
    }
}
