package com.example.wid.service;

import com.example.wid.dto.RegisterDTO;
import com.example.wid.entity.MemberEntity;
import com.example.wid.entity.enums.Role;
import com.example.wid.controller.exception.AlreadyExistsMemberException;
import com.example.wid.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.*;
import java.util.Base64;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // 회원가입
    public boolean registerMember(RegisterDTO registerDTO, Role role) throws AlreadyExistsMemberException, NoSuchAlgorithmException {
        // 이미 존재하는 아이디인지 확인
        if(isExistsMember(registerDTO)){
            throw new AlreadyExistsMemberException();
        }
        // 정보가 비어있는지 확인
        if(isInfoEmpty(registerDTO)){
            throw new IllegalArgumentException("모든 정보를 입력해주세요.");
        }

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyGenerator = keyPairGenerator.genKeyPair(); // 키생성기

        PublicKey publicKey = keyGenerator.getPublic(); // 공개키 생성
        PrivateKey privateKey = keyGenerator.getPrivate(); // 비공개키 생성
        String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        // MemberEntity 생성 및 아이디 비밀번호 저장
        MemberEntity member = MemberEntity.builder()
                .username(registerDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(registerDTO.getPassword()))
                .name(registerDTO.getName())
                .email(registerDTO.getEmail())
                .phone(registerDTO.getPhone())
                .role(role)
                .privateKey(encodedPrivateKey)
                .publicKey(encodedPublicKey)
                .build();
        memberRepository.save(member);
        return true;
    }

    // 이미 존재하는 회원인지 확인하는 메소드
    private boolean isExistsMember(RegisterDTO registerDTO) {
        String username = registerDTO.getUsername();
        String email = registerDTO.getEmail();
        String phone = registerDTO.getPhone();
        return memberRepository.existsByUsernameOrEmailOrPhone(username, email, phone);
    }
    // 정보가 비었는지 확인하는 메소드
    private boolean isInfoEmpty(RegisterDTO registerDTO){
        return registerDTO.getUsername().isEmpty() || registerDTO.getPassword().isEmpty() || registerDTO.getName().isEmpty() || registerDTO.getEmail().isEmpty() || registerDTO.getPhone().isEmpty();
    }
    private void generateRsaKeyPair() {
    }
}
