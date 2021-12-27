package kr.co.demo.son.demo.src.dto.member;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MobileCertificationDto {
    private String groupId;
    private String to;
    private String from;
    private String type;
    private String statusMessage;
    private String country;
    private String messageId;
    private String statusCode;
    private String accountId;
}
