package com.example.wid.entity;

import com.example.wid.entity.base.BaseCertificateEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "competition_certificate")
@Getter
@Setter
@NoArgsConstructor
public class CompetitionCertificateEntity implements BaseCertificateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_info_id")
    private CertificateInfoEntity certificateInfo;
    private String competitionName;
    private String achievement;
    private String organizer;
    private String summary;
    private String term;

    private String originalFilename;
    private String storedFilename;

    @Builder
    public CompetitionCertificateEntity(CertificateInfoEntity certificateInfo, String competitionName, String achievement, String organizer, String summary, String term, String originalFilename, String storedFilename) {
        this.certificateInfo = certificateInfo;
        this.competitionName = competitionName;
        this.achievement = achievement;
        this.organizer = organizer;
        this.summary = summary;
        this.term = term;
        this.originalFilename = originalFilename;
        this.storedFilename = storedFilename;
    }

    public String serializeCertificateForSignature() {
        String serializedCompetitionCertificate =
                "\"competitionName\": \"" + competitionName + "\",\n"
                + "\"achievement\": \"" + achievement + "\",\n"
                + "\"organizer\": \"" + organizer + "\",\n"
                + "\"summary\": \"" + summary + "\",\n"
                + "\"term\": \"" + term + "\"\n";
        return serializedCompetitionCertificate;
    }
}
