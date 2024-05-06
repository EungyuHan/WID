package com.example.wid.service;

import com.example.wid.dto.RegisterDTO;
import com.example.wid.entity.MemberEntity;
import com.example.wid.entity.Role;
import com.example.wid.entity.RoleEntity;
import com.example.wid.controller.exception.AlreadyExistsMemberException;
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

    public boolean registerMember(RegisterDTO registerDTO, Role role) throws AlreadyExistsMemberException {
        // 이미 존재하는 아이디인지 확인
        if(isExistsMember(registerDTO)){
            throw new AlreadyExistsMemberException();
        }
        // 정보가 비어있는지 확인
        if(isInfoEmpty(registerDTO)){
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
        Optional<RoleEntity> roleUser = roleRepository.findByRole(role);
        if (!roleUser.isPresent()) {
            return false;
        } else {
            member.setRole(roleUser.get());
            memberRepository.save(member);
            return true;
        }
    }

    private boolean isExistsMember(RegisterDTO registerDTO) {
        String username = registerDTO.getUsername();
        String email = registerDTO.getEmail();
        String phone = registerDTO.getPhone();
        return memberRepository.existsByUsernameOrEmailOrPhone(username, email, phone);
    }
    // 정보가 비었는지 확인
    public boolean isInfoEmpty(RegisterDTO registerDTO){
        return registerDTO.getUsername().isEmpty() || registerDTO.getPassword().isEmpty() || registerDTO.getName().isEmpty() || registerDTO.getEmail().isEmpty() || registerDTO.getPhone().isEmpty();
    }
}
