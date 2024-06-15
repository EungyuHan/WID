package com.example.wid.dto;

import com.example.wid.dto.base.BaseCertificateJson;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeName("CompetitionCertificate")
public class CompetitionCertificateJson implements BaseCertificateJson {
    private Long id;
    private String competitionName;
    private String achievement;
    private String organizer;
    private String summary;
    private String term;

    @Builder
    public CompetitionCertificateJson(Long id, String competitionName, String achievement, String organizer, String summary, String term) {
        this.id = id;
        this.competitionName = competitionName;
        this.achievement = achievement;
        this.organizer = organizer;
        this.summary = summary;
        this.term = term;
    }
}
