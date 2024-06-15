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
@JsonTypeName("ClassCertificate")
public class ClassCertificateJson implements BaseCertificateJson {
    private Long id;
    private String name;
    private String studentId;
    private String subject;
    private String professor;
    private String summary;
    private String term;

    @Builder
    public ClassCertificateJson(Long id, String name, String studentId, String subject, String professor, String summary, String term) {
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.subject = subject;
        this.professor = professor;
        this.summary = summary;
        this.term = term;
    }
}
