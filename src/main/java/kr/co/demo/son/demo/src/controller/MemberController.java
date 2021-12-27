package kr.co.demo.son.demo.src.controller;


import io.swagger.v3.oas.annotations.Operation;
import kr.co.demo.son.demo.src.dto.member.MemberMgtDto;
import kr.co.demo.son.demo.src.dto.member.MemberPasswordDto;
import kr.co.demo.son.demo.src.dto.member.SaveMemberDto;
import kr.co.demo.son.demo.src.service.MemberService;
import kr.co.demo.son.demo.src.system.exception.BadRequestException;
import kr.co.demo.son.demo.src.system.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(headers = "X-API-VERSION=1")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 가입")
    @RequestMapping(value = "/member", method = RequestMethod.POST)
    public ResponseEntity<?> saveMember(@RequestBody SaveMemberDto dto) {

        saveValidation(dto);
        Long id = memberService.saveMember(dto);

        log.info("회원가입 성공 : {}", id);
        return ResponseEntity.ok().body(new CommonResponse<>(id));
    }

    @Operation(summary = "회원 핸드폰 인증", description = "인증 번호 반환 front에서 입력 값 비교 필요")
    @RequestMapping(value = "/member/certification", method = RequestMethod.GET)
    public ResponseEntity<?> getMobileCertification(@RequestParam String mobileNo) {

        mobileCertificationValidation(mobileNo);

        String result = memberService.getMobileCertification(mobileNo);

        log.info("핸드폰 인증 여부 : {}", result);
        return ResponseEntity.ok().body(new CommonResponse<>(result));
    }

    @Operation(summary = "회원 비밀번호 수정", description = "핸드폰 번호로 검증 후 변경할 비밀번호 입력")
    @RequestMapping(value = "/member/password", method = RequestMethod.PUT)
    public ResponseEntity<?> updateMemberPassword(@RequestBody MemberPasswordDto dto) {
        updateMemberPasswordValidation(dto);

        String result = memberService.updateMemberPassword(dto);
        return ResponseEntity.ok().body(new CommonResponse<>(result));
    }

    @Operation(summary = "회원 정보 (단 건) 조회", description = "내 정보 보기")
    @RequestMapping(value = "/member/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMemberMgt(@PathVariable Long id) {
        MemberMgtDto result = memberService.getMemberMgt(id);
        return ResponseEntity.ok().body(new CommonResponse<>(result));
    }

    /**
     * 저장 유효성 검증 메서드
     * */
    public void saveValidation(SaveMemberDto dto) {
        if (dto.getEmail() == null || dto.getEmail().length() == 0) {
            throw new BadRequestException("이메일 필수 값 입니다.", HTTP_BAD_REQUEST);
        }
        if (dto.getName() == null || dto.getName().length() == 0) {
            throw new BadRequestException("이름은 필수 값 입니다.", HTTP_BAD_REQUEST);
        }
        if (dto.getPassword() == null || dto.getPassword().length() == 0) {
            throw new BadRequestException("비밀번호는 필수 값 입니다.", HTTP_BAD_REQUEST);
        }
        if (dto.getMobileNo() == null || dto.getMobileNo().length() == 0) {
            throw new BadRequestException("핸드폰 번호는 필수 값 입니다.", HTTP_BAD_REQUEST);
        }
        if (dto.getEmail().length() > 35) {
            throw new BadRequestException("이메일 최대 글자 크기를 초과했습니다.", HTTP_BAD_REQUEST);
        }
        if (dto.getName().length() > 25) {
            throw new BadRequestException("이름 최대 글자 크기를 초과했습니다.", HTTP_BAD_REQUEST);
        }
        if (dto.getNickname().length() > 35) {
            throw new BadRequestException("닉네임 최대 글자 크기를 초과했습니다.", HTTP_BAD_REQUEST);
        }
        if (dto.getPassword().length() > 95) {
            throw new BadRequestException("비밀번호 최대 글자 크기를 초과했습니다.", HTTP_BAD_REQUEST);
        }
        if (dto.getMobileNo().length() > 35) {
            throw new BadRequestException("전화번호 최대 글자 크기를 초과했습니다.", HTTP_BAD_REQUEST);
        }
        if (!dto.getMobileNo().matches("(01[016789])(\\d{3,4})(\\d{4})")) {
            throw new BadRequestException("정상적인 핸드폰 번호를 입력해주세요. ex) 01012345678", HTTP_BAD_REQUEST);
        }
    }
    public void updateMemberPasswordValidation(MemberPasswordDto dto) {
        if (dto.getPassword() == null || dto.getPassword().length() == 0) {
            throw new BadRequestException("비밀번호는 필수 값 입니다.", HTTP_BAD_REQUEST);
        }
        if (dto.getMobileNo() == null || dto.getMobileNo().length() == 0) {
            throw new BadRequestException("핸드폰 번호는 필수 값 입니다.", HTTP_BAD_REQUEST);
        }
        if (!dto.getMobileNo().matches("(01[016789])(\\d{3,4})(\\d{4})")) {
            throw new BadRequestException("정상적인 핸드폰 번호를 입력해주세요. ex) 01012345678", HTTP_BAD_REQUEST);
        }
    }
    public void mobileCertificationValidation(String mobileNo) {
        if (mobileNo == null || mobileNo.length() == 0) {
            throw new BadRequestException("핸드폰 번호는 필수 값 입니다.", HTTP_BAD_REQUEST);
        }
        if (!mobileNo.matches("(01[016789])(\\d{3,4})(\\d{4})")) {
            throw new BadRequestException("정상적인 핸드폰 번호를 입력해주세요. ex) 01012345678", HTTP_BAD_REQUEST);
        }
    }
}
