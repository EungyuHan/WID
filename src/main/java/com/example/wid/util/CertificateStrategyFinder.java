package com.example.wid.util;

import com.example.wid.entity.enums.CertificateType;
import com.example.wid.service.strategy.CertificateStrategy;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CertificateStrategyFinder {
    private final List<CertificateStrategy> strategies;

    @Autowired
    public CertificateStrategyFinder(List<CertificateStrategy> strategies) {
        this.strategies = strategies;
    }

    public CertificateStrategy find(CertificateType type) {
        return strategies.stream()
                .filter(strategy -> strategy.getType().equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 전략이 없습니다."));
    }
}
