package com.example.wid.entity;

import com.example.wid.entity.base.BaseEntity;
import com.example.wid.entity.enums.CertificateType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 증명서에 대한 소유등을 저장하기 위한 클래스
@Entity(name = "certificate_info")
@Getter
@Setter
@NoArgsConstructor
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

    @Builder
    public CertificateInfoEntity(MemberEntity user, MemberEntity issuer, CertificateType certificateType, String storedFilename, String originalFilename, SignatureInfoEntity signatureInfo) {
        this.user = user;
        this.issuer = issuer;
        this.certificateType = certificateType;
        this.storedFilename = storedFilename;
        this.originalFilename = originalFilename;
        this.signatureInfo = signatureInfo;
    }
}
