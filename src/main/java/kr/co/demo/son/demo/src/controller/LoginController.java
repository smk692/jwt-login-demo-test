package kr.co.demo.son.demo.src.controller;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.demo.son.demo.src.dto.login.LoginDto;
import kr.co.demo.son.demo.src.dto.login.LoginResponseDto;
import kr.co.demo.son.demo.src.dto.member.MemberPasswordDto;
import kr.co.demo.son.demo.src.service.LoginService;
import kr.co.demo.son.demo.src.system.exception.BadRequestException;
import kr.co.demo.son.demo.src.system.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(headers = "X-API-VERSION=1")
public class LoginController {

    private final LoginService loginService;

    /**
     * 로그인
     * @return ResponseEntity
     *
     */
    @Operation(summary = "로그인", description = "핸드폰 번호 id, password 로 조회")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> getLogin(@RequestBody LoginDto dto) {
        loginValidation(dto);

        LoginResponseDto result = loginService.getLogin(dto);
        return ResponseEntity.ok().body(new CommonResponse<>(result));
    }

    public void loginValidation(LoginDto dto) {
        if (dto.getMobileNo() == null || dto.getMobileNo().length() == 0) {
            throw new BadRequestException("핸드폰 번호는 필수 값 입니다.", HTTP_BAD_REQUEST);
        }
        if (!dto.getMobileNo().matches("(01[016789])(\\d{3,4})(\\d{4})")) {
            throw new BadRequestException("정상적인 핸드폰 번호를 입력해주세요. ex) 01012345678", HTTP_BAD_REQUEST);
        }
    }
}
