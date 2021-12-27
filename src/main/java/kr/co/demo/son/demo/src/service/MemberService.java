package kr.co.demo.son.demo.src.service;

import kr.co.demo.son.demo.src.domain.Member.Member;
import kr.co.demo.son.demo.src.domain.Member.MemberRepository;
import kr.co.demo.son.demo.src.dto.member.MemberMgtDto;
import kr.co.demo.son.demo.src.dto.member.MemberPasswordDto;
import kr.co.demo.son.demo.src.dto.member.SaveMemberDto;
import kr.co.demo.son.demo.src.enumType.EnumType;
import kr.co.demo.son.demo.src.system.exception.BadRequestException;
import kr.co.demo.son.demo.src.utils.SmsMessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final SmsMessageUtil smsMessageUtil;

    @Transactional
    public Long saveMember(SaveMemberDto dto) {

        Optional<Member> findMember = memberRepository.findByMobileNoAndActiveYn(dto.getMobileNo(), EnumType.YNType.Y);

        if(findMember.isPresent()) {
            throw new BadRequestException("이미 회원가입된 번호입니다.", HTTP_BAD_REQUEST);
        }

        String hashed_password = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());

        return memberRepository.save(new Member(
                dto.getEmail(),
                dto.getName(),
                dto.getNickname(),
                hashed_password,
                dto.getMobileNo())
        ).getId();
    }

    public String getMobileCertification(String mobileNo) {
        String number = String.valueOf(new Random().nextInt(99999));

        try {
            smsMessageUtil.sendMessage(mobileNo, number);
        } catch (CoolsmsException e) {
            throw new BadRequestException("전송에 실패했습니다." + e.getMessage(), HTTP_BAD_REQUEST);
        }
        return number;
    }

    @Transactional
    public String updateMemberPassword(MemberPasswordDto dto) {
        Optional<Member> findMember = memberRepository.findByMobileNo(dto.getMobileNo());

        if (!findMember.isPresent()) {
            throw new BadRequestException("없는 사용자 입니다.", HTTP_BAD_REQUEST);
        }

        Member info = findMember.get();
        if (info.getActiveYn().equals(EnumType.YNType.N)) {
            throw new BadRequestException("탈퇴한 사용자 입니다.", HTTP_BAD_REQUEST);
        }

        String hashed_password = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
        info.updateMemberPassword(hashed_password);

        return "정상적으로 변경 되었습니다.";
    }

    public MemberMgtDto getMemberMgt(Long id) {
        Optional<Member> findMember = memberRepository.findByIdAndActiveYn(id, EnumType.YNType.Y);

        if(!findMember.isPresent()) {
            throw new BadRequestException("데이터를 확인해주세요.", HTTP_BAD_REQUEST);
        }
        return entityToMemberMgtDto(findMember.get());
    }

    public MemberMgtDto entityToMemberMgtDto(Member e) {
        return new MemberMgtDto().builder()
                .id(e.getId())
                .email(e.getEmail())
                .mobileNo(e.getMobileNo())
                .name(e.getName())
                .nickname(e.getNickname())
                .mobileCertificationYn(e.getMobileCertificationYn())
                .build();
    }
}
