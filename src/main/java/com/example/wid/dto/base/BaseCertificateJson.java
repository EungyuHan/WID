package com.example.wid.dto.base;

import com.example.wid.dto.ClassCertificateJson;
import com.example.wid.dto.CompetitionCertificateJson;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClassCertificateJson.class, name = "ClassCertificate"),
        @JsonSubTypes.Type(value = CompetitionCertificateJson.class, name = "CompetitionCertificate")
})
public interface BaseCertificateJson {
}
