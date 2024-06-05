package com.example.wid.service;

import com.example.wid.entity.MemberEntity;
import com.example.wid.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.security.*;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RsaServiceTest {
    @Autowired
    private RsaService rsaService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("RSA 키 생성 성공")
    void generateRsaKeyPair() {
        MemberEntity memberEntity = MemberEntity.builder()
                .username("user")
                .password("userpassword")
                .email("user@user.com")
                .name("user")
                .phone("01011111111")
                .build();
        MemberEntity savedUser = memberRepository.save(memberEntity);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getUsername(), savedUser.getPassword());

        rsaService.generateRsaKeyPair(authentication);

        MemberEntity user = memberRepository.findByUsername(savedUser.getUsername()).get();
        assertNotNull(user.getPublicKey());
        assertNotNull(user.getPrivateKey());
    }

    @Test
    @DisplayName("RSA 키 생성 실패 - 이미 키가 존재하는 경우")
    void generateRsaKeyPairFail() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyGenerator = keyPairGenerator.genKeyPair();

        PrivateKey privateKey = keyGenerator.getPrivate();
        String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        PublicKey publicKey = keyGenerator.getPublic();
        String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        MemberEntity memberEntity = MemberEntity.builder()
                .username("user")
                .password("userpassword")
                .email("user@user.com")
                .name("user")
                .phone("01011111111")
                .publicKey(encodedPublicKey)
                .privateKey(encodedPrivateKey)
                .build();
        MemberEntity savedUser = memberRepository.save(memberEntity);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getUsername(), savedUser.getPassword());

        assertThrows(IllegalArgumentException.class, () -> rsaService.generateRsaKeyPair(authentication));
    }
}