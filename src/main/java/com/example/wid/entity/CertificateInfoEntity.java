package com.example.wid.entity;

import com.example.wid.entity.base.BaseEntity;
import com.example.wid.entity.enums.CertificateType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "certificate_info")
@Getter
@Setter
public class CertificateInfoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private MemberEntity user;
    @OneToOne
    @JoinColumn(name = "issuer_id")
    private MemberEntity issuer;
    @Column(name = "certificate_type")
    @Enumerated(EnumType.STRING)
    private CertificateType certificateType;

    private String storedFilename;
    private String originalFilename;

    @OneToOne
    @JoinColumn(name = "signature_info_id", nullable = true)
    private SignatureInfoEntity signatureInfo;

    @OneToOne(mappedBy = "certificateInfo")
    private ClassCertificateEntity classCertificate;
}
