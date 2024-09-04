package com.example.wid.entity;

import com.example.wid.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "encrypt_info")
@Getter
@Setter
@NoArgsConstructor
// 서명한 정보를 담는 클래스
public class EncryptInfoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String issuerEncrypt;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String issuerPublicKey;
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_info_id")
    CertificateInfoEntity certificateInfo;

    // 블록체인 연동 후 삭제
     @Column(columnDefinition = "TEXT")
    private String userEncrypt;

    @Builder
    public EncryptInfoEntity(String issuerEncrypt, String userEncrypt, String issuerPublicKey, CertificateInfoEntity certificateInfo) {
        this.issuerEncrypt = issuerEncrypt;
        this.userEncrypt = userEncrypt;
        this.issuerPublicKey = issuerPublicKey;
        this.certificateInfo = certificateInfo;
    }
}
