package kr.co.demo.son.demo.src.domain.Member;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.demo.son.demo.src.system.context.TestContextHolder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import kr.co.demo.son.demo.src.enumType.EnumType.*;
import static javax.persistence.EnumType.STRING;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "member")
@Table(name = "member")
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 이메일
    @Column(name = "email", nullable = false)
    private String email;

    // 이름
    @Column(name = "name", nullable = false)
    private String name;

    // 닉네임
    @Column(name = "nickname")
    private String nickname;

    // 비밀번호
    @Column(name = "password", nullable = false)
    private String password;

    // 핸드폰 번호
    @Column(name = "mobile_no", nullable = false)
    private String mobileNo;

    // 핸드폰 인증 여부
    @Column(name = "mobile_certification_yn", nullable = false, columnDefinition = "ENUM('Y','N')")
    @Enumerated(STRING)
    private MobileCertificationYn mobileCertificationYn;

    // 데이터 활성화 여부
    @Column(name = "active_yn", nullable = false, columnDefinition = "ENUM('Y','N')")
    @Enumerated(STRING)
    private YNType activeYn;

    // 생성일
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // 수정일
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 생성자
    @Column(name = "created_worker_id", nullable = false)
    private int createdWorkerId;

    // 수정자
    @Column(name = "updated_worker_id")
    private Integer updatedWorkerId;

    @PrePersist
    public void PrePersist() {
        Integer userId = TestContextHolder.getContext().getUserId();

        if(userId == null) {
            userId = 9999999;
        }

        this.mobileCertificationYn = MobileCertificationYn.N;
        this.activeYn = YNType.Y;
        this.createdAt = LocalDateTime.now();
        this.createdWorkerId = userId;
    }
    @PostPersist
    public void PostPersist() {
        this.updatedAt = LocalDateTime.now();
        this.updatedWorkerId = TestContextHolder.getContext().getUserId();
    }

    /**
     *  회원 가입 생성자
     */
    public Member(String email, String name, String nickname, String password, String mobileNo) {
        // 이메일
        this.email = email;
        // 이름
        this.name = name;
        // 닉네임
        this.nickname = nickname;
        // 비밀번호
        this.password = password;
        // 핸드폰 번호
        this.mobileNo = mobileNo;
    }
    /**
     * 회원 비밀번호 변경
     * */
    public void updateMemberPassword(String password) {
        this.password = password;
    }
}