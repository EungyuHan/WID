package com.example.wid.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity(name = "signature_info")
@Getter
@Setter
@NoArgsConstructor
// 서명한 정보를 담는 클래스
public class SignatureInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String issuerSignature;
    @Column(columnDefinition = "TEXT", nullable = true)
    private String userSignature;
    @Column(columnDefinition = "TEXT")
    private String issuerPublicKey;
    @Column(columnDefinition = "TEXT", nullable = true)
    private String userPublicKey;
    private Date issuerSignedAt;
    @Column(nullable = true)
    private Date userSignedAt;
    @ColumnDefault("false")
    private boolean isUserSigned;

    @Builder
    public SignatureInfoEntity(String issuerSignature, String userSignature, String issuerPublicKey, String userPublicKey, Date issuerSignedAt, Date userSignedAt, boolean isUserSigned) {
        this.issuerSignature = issuerSignature;
        this.userSignature = userSignature;
        this.issuerPublicKey = issuerPublicKey;
        this.userPublicKey = userPublicKey;
        this.issuerSignedAt = issuerSignedAt;
        this.userSignedAt = userSignedAt;
        this.isUserSigned = isUserSigned;
    }
}
