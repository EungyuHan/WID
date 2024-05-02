package com.example.wid.service;

import com.example.wid.dto.RegisterDTO;
import com.example.wid.entity.MemberEntity;
import com.example.wid.entity.Role;
import com.example.wid.entity.RoleEntity;
import com.example.wid.exception.AlreadyExistsMemberException;
import com.example.wid.repository.MemberRepository;
import com.example.wid.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean registerUser(RegisterDTO registerDTO) throws AlreadyExistsMemberException {
        // 이미 존재하는 아이디인지 확인
        if(memberRepository.existsByUsernameOrEmailOrPhone(registerDTO.getUsername(), registerDTO.getEmail(), registerDTO.getPhone())){
            throw new AlreadyExistsMemberException();
        }
        // 정보가 비어있는지 확인
        if(registerDTO.getUsername().isEmpty() || registerDTO.getPassword().isEmpty() || registerDTO.getName().isEmpty() || registerDTO.getEmail().isEmpty() || registerDTO.getPhone().isEmpty()){
//            return false;
            throw new IllegalArgumentException("모든 정보를 입력해주세요.");
        }
        // MemberEntity 생성 및 아이디 비밀번호 저장
        MemberEntity member = new MemberEntity();
        member.setUsername(registerDTO.getUsername());
        member.setPassword(bCryptPasswordEncoder.encode(registerDTO.getPassword()));
        member.setName(registerDTO.getName());
        member.setEmail(registerDTO.getEmail());
        member.setPhone(registerDTO.getPhone());

        // Member의 권한지정
        // 일반 사용자 권한을 찾아서 없으면 회원가입 실패
        Optional<RoleEntity> roleUser = roleRepository.findByRole(Role.ROLE_USER);
        if (!roleUser.isPresent()) {
            // 일반 사용자 권한이 없으면 회원가입 실패
            return false;
        } else {
            // 일반 사용자 권한이 있으면 회원가입 성공
            member.setRole(roleUser.get());
            memberRepository.save(member);
            return true;
        }
    }
}
