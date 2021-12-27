package kr.co.demo.son.demo.src.domain.Member;

import kr.co.demo.son.demo.src.enumType.EnumType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByMobileNoAndActiveYn(String mobileNo, EnumType.YNType y);

    Optional<Member> findByIdAndActiveYn(Long id, EnumType.YNType y);

    Optional<Member> findByMobileNo(String mobileNo);
}