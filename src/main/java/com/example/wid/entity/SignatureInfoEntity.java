package com.example.wid.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity(name = "signature_info")
@Getter
@Setter
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
}
