package com.example.wid.entity;

import com.example.wid.entity.base.BaseEntity;
import com.example.wid.entity.enums.CertificateType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "user_certificate_info")
@Getter
@Setter
public class UserCertificateInfoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @Column(name = "user_id")
    private MemberEntity user;
    @OneToOne
    @Column(name = "issuer_id")
    private MemberEntity issuer;
    @Column(name = "certificate_type")
    @Enumerated(EnumType.STRING)
    private CertificateType certificateType;

    private String storedFilename;
    private String originalFilename;

    @OneToOne
    @Column(name = "class_certificate_id", nullable = true)
    private ClassCertificateEntity classCertificate;

    @OneToOne
    private SignatureInfoEntity signatureInfo;
}
