package com.example.wid.entity;

import jakarta.persistence.*;

@Entity(name = "affiliation")
public class AffiliationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String affiliationName;
    @Enumerated(EnumType.STRING)
    private Affiliation affiliationType;
}
