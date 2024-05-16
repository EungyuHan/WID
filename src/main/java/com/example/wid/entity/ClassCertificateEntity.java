package com.example.wid.entity;

import com.example.wid.entity.base.BaseCertificateEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity(name = "class_certificate")
@Getter
@Setter
// 전자서명 중 수업에 대한 증명을 위한 인증서를 저장하기 위한 클래스
public class ClassCertificateEntity extends BaseCertificateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private String professor;
    private Date startDate;
    private Date endDate;
}
