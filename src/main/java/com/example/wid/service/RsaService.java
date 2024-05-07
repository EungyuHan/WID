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
    public Optional<String> generateRsaKeyPair(Authentication authentication) throws NoSuchAlgorithmException {
        // RSA 비대칭키 생성
        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);

            PublicKey publicKey = keyPairGenerator.genKeyPair().getPublic();
            PrivateKey privateKey = keyPairGenerator.genKeyPair().getPrivate();
            String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

            String username = authentication.getName();

            memberRepository.updatePublicKeyByUsername(username, encodedPrivateKey);

            return Optional.of(encodedPublicKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
