package com.example.wid.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ClassCertificateDTO {
    private String major;
    private String subject;
    private String professor;
    private String detail;
    private String startDate;
    private String endDate;
    // 수업 인증서 발급자 이메일
    // 이슈어가 누군지 식별하기 위함
    private String issuerEmail;

    private MultipartFile file;
}
