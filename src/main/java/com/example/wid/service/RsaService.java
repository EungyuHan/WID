package com.example.wid.service;

import com.example.wid.controller.exception.EncryptionException;
import com.example.wid.controller.exception.UserNotFoundException;
import com.example.wid.entity.MemberEntity;
import com.example.wid.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public void generateRsaKeyPair(Authentication authentication) {
        try{
            MemberEntity user = null;
            if(memberRepository.findByUsername(authentication.getName()).isPresent()){
                user = memberRepository.findByUsername(authentication.getName()).get();
            } else throw new UserNotFoundException();

            if(user.getPublicKey() != null || user.getPrivateKey() != null){
                throw new IllegalArgumentException("이미 키가 존재합니다.");
            }

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyGenerator = keyPairGenerator.genKeyPair(); // 키생성기

            PublicKey publicKey = keyGenerator.getPublic(); // 공개키 생성
            PrivateKey privateKey = keyGenerator.getPrivate(); // 비공개키 생성
            String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

            user.setPublicKey(encodedPublicKey);
            user.setPrivateKey(encodedPrivateKey);
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException("키 생성에 실패하였습니다.");
        }
    }
}
