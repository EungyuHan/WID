package com.example.wid.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
// 전자서명시 사용되는 인증서의 정보를 담는 클래스
// 해당 클래스를 상속받는 모든 클래스는 인증서에 담기는 정보를 의미
public class BaseCertificateEntity {
    // 사용자의 정보를 바탕으로 입력되는 정보
    private String name;
    private String belong;

    // 사용자가 직접 입력해야하는 정보
    private String major;
    @Column(columnDefinition = "TEXT")
    private String detail;
}
