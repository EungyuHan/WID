package com.example.wid.service;

import com.example.wid.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.*;
import java.util.Base64;
import java.util.Optional;

@Service
public class RsaService {
    private final MemberRepository memberRepository;
    @Autowired
    public RsaService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    // RSA 비대칭키 생성
    public Optional<String> generateRsaKeyPair(Authentication authentication) throws NoSuchAlgorithmException {
        // RSA 비대칭키 생성
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyGenerator = keyPairGenerator.genKeyPair(); // 키생성기

        PublicKey publicKey = keyGenerator.getPublic(); // 공개키 생성
        PrivateKey privateKey = keyGenerator.getPrivate(); // 비공개키 생성
        String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        String username = authentication.getName();

        memberRepository.updatePublicKeyByUsername(username, encodedPublicKey);

        return Optional.of(encodedPrivateKey);
    }
}
