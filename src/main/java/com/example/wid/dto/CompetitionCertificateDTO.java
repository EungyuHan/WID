package com.example.wid.dto;

import com.example.wid.controller.exception.InvalidCertificateException;
import com.example.wid.dto.base.BaseCertificateDTO;
import com.example.wid.entity.CompetitionCertificateEntity;
import com.example.wid.entity.base.BaseCertificateEntity;
import com.example.wid.entity.enums.CertificateType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class CompetitionCertificateDTO implements BaseCertificateDTO {
    private String competitionName;
    private String achievement;
    private String organizer;
    private String summary;

    private String startDate;
    private String endDate;
    private String issuerEmail;

    private MultipartFile file;

    @Builder
    public CompetitionCertificateDTO(String competitionName, String achievement, String organizer, String summary, String startDate, String endDate, String issuerEmail, MultipartFile file) {
        this.competitionName = competitionName;
        this.achievement = achievement;
        this.organizer = organizer;
        this.summary = summary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.issuerEmail = issuerEmail;
        this.file = file;
    }

    @Override
    public BaseCertificateEntity toCertificateEntity(CertificateType certificateType) {
        if(certificateType != CertificateType.COMPETITION_CERTIFICATE)
            throw new InvalidCertificateException("잘못된 증명서 정보입니다.");

        return CompetitionCertificateEntity.builder()
                .competitionName(this.competitionName)
                .achievement(this.achievement)
                .organizer(this.organizer)
                .summary(this.summary)
                .term(this.getStartDate() + " ~ " + this.getEndDate())
                .build();
    }
}
