package com.example.wid.entity;

import com.example.wid.dto.ClassCertificateDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity(name = "class_certificate")
@Getter
@Setter
// 전자서명 중 수업에 대한 증명을 위한 인증서를 저장하기 위한 클래스
public class ClassCertificateEntity {
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

    private String belong;

    public static ClassCertificateEntity createClassCertificate(ClassCertificateDTO classCertificateDTO) {
        ClassCertificateEntity classCertificateEntity = new ClassCertificateEntity();
        classCertificateEntity.setStudentId(classCertificateDTO.getStudentId());
        classCertificateEntity.setSubject(classCertificateDTO.getSubject());
        classCertificateEntity.setProfessor(classCertificateDTO.getProfessor());
        classCertificateEntity.setSummary(classCertificateDTO.getSummary());
        String term = classCertificateDTO.getStartDate() + " ~ " + classCertificateDTO.getEndDate();
        classCertificateEntity.setTerm(term);
        return classCertificateEntity;
    }

    public static String serializeClassCertificateForSignature(ClassCertificateEntity classCertificateEntity) {
        String serializedClassCertificate = "{\n"
                + "\"name\": \"" + classCertificateEntity.getName() + "\",\n"
                + "\"belong\": \"" + classCertificateEntity.getBelong() + "\"\n"
                + "\"subject\": \"" + classCertificateEntity.getSubject() + "\",\n"
                + "\"professor\": \"" + classCertificateEntity.getProfessor() + "\",\n"
                + "\"summary\": \"" + classCertificateEntity.getSummary() + "\",\n"
                + "\"term\": \"" + classCertificateEntity.getTerm() + "\"\n"
                + "}";
        return serializedClassCertificate;
    }
}
