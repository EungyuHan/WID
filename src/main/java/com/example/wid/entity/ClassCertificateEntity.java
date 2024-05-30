package com.example.wid.entity;

import com.example.wid.entity.base.BaseCertificateEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "class_certificate")
@Getter
@Setter
@NoArgsConstructor
// 전자서명 중 수업에 대한 증명을 위한 인증서를 저장하기 위한 클래스
public class ClassCertificateEntity implements BaseCertificateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_info_id")
    private CertificateInfoEntity certificateInfo;

    private String name;
    private String studentId;
    private String subject;
    private String professor;
    private String summary;
    private String term;

    @Builder
    public ClassCertificateEntity(CertificateInfoEntity certificateInfo, String name, String studentId, String subject, String professor, String summary, String term) {
        this.certificateInfo = certificateInfo;
        this.name = name;
        this.studentId = studentId;
        this.subject = subject;
        this.professor = professor;
        this.summary = summary;
        this.term = term;
    }

    @Override
    public String serializeCertificateForSignature() {
        String serializedClassCertificate =
                "\"name\": \"" + name + "\",\n"
                + "\"studentId\": \"" + studentId + "\",\n"
                + "\"subject\": \"" + subject + "\",\n"
                + "\"professor\": \"" + professor + "\",\n"
                + "\"summary\": \"" + summary + "\",\n"
                + "\"term\": \"" + term + "\"\n";
        return serializedClassCertificate;
    }
}
