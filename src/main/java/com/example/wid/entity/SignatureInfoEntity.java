package com.example.wid.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity(name = "signature_info")
@Getter
@Setter
// 서명한 정보를 담는 클래스
public class SignatureInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String signature;
    private Date issuerSignedAt;
    private Date userSignedAt;
    @ColumnDefault("false")
    private boolean isUserSigned;
}
