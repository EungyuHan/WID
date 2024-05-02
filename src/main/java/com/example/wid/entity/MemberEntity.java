package com.example.wid.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member")
public class MemberEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;
    @OneToOne
    @JoinColumn(name = "affiliation_id")
    private AffiliationEntity affiliation;
    @Column(unique = true)
    private String username;
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;
    @Column(unique = true)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    private String name;
    @Column(nullable = true)
    private String phone;
    @Column(nullable = true)
    private String wallet;

    public String getRole() {
        return role.getRole().name();
    }
}