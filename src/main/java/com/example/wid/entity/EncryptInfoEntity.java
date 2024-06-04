package com.example.wid.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity(name = "encrypt_info")
@Getter
@Setter
@NoArgsConstructor
// 서명한 정보를 담는 클래스
public class EncryptInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String issuerEncrypt;
    @Column(columnDefinition = "TEXT")
    private String userEncrypt;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String issuerPublicKey;
    @Column(columnDefinition = "TEXT")
    private String userPublicKey;
    private Date issuerSignedAt;
    private Date userSignedAt;
    @ColumnDefault("false")
    private Boolean isUserSigned;

    @Builder
    public EncryptInfoEntity(String issuerEncrypt, String userEncrypt, String issuerPublicKey, String userPublicKey, Date issuerSignedAt, Date userSignedAt, boolean isUserSigned) {
        this.issuerEncrypt = issuerEncrypt;
        this.userEncrypt = userEncrypt;
        this.issuerPublicKey = issuerPublicKey;
        this.userPublicKey = userPublicKey;
        this.issuerSignedAt = issuerSignedAt;
        this.userSignedAt = userSignedAt;
        this.isUserSigned = isUserSigned;
    }
}
