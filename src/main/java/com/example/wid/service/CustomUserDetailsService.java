package com.example.wid.service;

import com.example.wid.entity.MemberEntity;
import com.example.wid.repository.MemberRepository;
import com.example.wid.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberEntity> member = memberRepository.findByUsername(username);
        if(member.isPresent()){
            return new CustomUserDetails(member.get());
        } else throw new UsernameNotFoundException("User not found with username: " + username);
    }
}