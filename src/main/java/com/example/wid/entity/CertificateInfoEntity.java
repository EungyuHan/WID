package com.example.wid.entity;

import com.example.wid.entity.base.BaseEntity;
import com.example.wid.entity.enums.CertificateType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

// 증명서에 대한 소유등을 저장하기 위한 클래스
@Entity(name = "certificate_info")
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class CertificateInfoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MemberEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issuer_id")
    private MemberEntity issuer;
    @Column(name = "certificate_type")
    @Enumerated(EnumType.STRING)
    private CertificateType certificateType;
    private String removedByte;
    @ColumnDefault("false")
    private Boolean isSigned;
    @OneToMany(mappedBy = "certificate", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FolderCertificateEntity> folderCertificates;

    @Builder
    public CertificateInfoEntity(MemberEntity user, MemberEntity issuer, CertificateType certificateType, String removedByte, Boolean isSigned) {
        this.user = user;
        this.issuer = issuer;
        this.certificateType = certificateType;
        this.removedByte = removedByte;
        this.isSigned = isSigned;
    }

    @JsonProperty("certificate_type")
    public CertificateType getCertificaType() {
        return certificateType;
    }
}
