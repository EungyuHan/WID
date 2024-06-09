package com.example.wid.service;

import com.example.wid.controller.exception.InvalidCertificateException;
import com.example.wid.controller.exception.InvalidFolderException;
import com.example.wid.controller.exception.UserNotFoundException;
import com.example.wid.controller.exception.VerifierNotFoundException;
import com.example.wid.dto.FolderCertificatesDTO;
import com.example.wid.entity.*;
import com.example.wid.repository.CertificateInfoRepository;
import com.example.wid.repository.FolderCertificateRepository;
import com.example.wid.repository.FolderRepository;
import com.example.wid.repository.MemberRepository;
import com.example.wid.repository.SentCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final FolderCertificateRepository folderCertificateRepository;
    private final CertificateInfoRepository certificateInfoRepository;
    private final MemberRepository memberRepository;
    private final SentCertificateRepository sentCertificateRepository;
    @Autowired
    public FolderService(FolderRepository folderRepository, FolderCertificateRepository folderCertificateRepository, CertificateInfoRepository certificateInfoRepository, MemberRepository memberRepository, SentCertificateRepository sentCertificateRepository) {
        this.folderRepository = folderRepository;
        this.folderCertificateRepository = folderCertificateRepository;
        this.certificateInfoRepository = certificateInfoRepository;
        this.memberRepository = memberRepository;
        this.sentCertificateRepository = sentCertificateRepository;
    }

    public void createFolder(String folderName, Authentication authentication) {
        MemberEntity user = null;
        if(memberRepository.findByUsername(authentication.getName()).isPresent()){
            user = memberRepository.findByUsername(authentication.getName()).get();
        } else throw new UserNotFoundException();

        FolderEntity folder = FolderEntity.builder()
                .folderName(folderName)
                .user(user)
                .build();
        folderRepository.save(folder);
    }

    public void insertCertificates(FolderCertificatesDTO folderCertificatesDTO, Authentication authentication) {
        FolderEntity folder = folderRepository.findById(folderCertificatesDTO.getFolderId())
                .orElseThrow(InvalidFolderException::new);

        MemberEntity user = memberRepository.findByUsername(authentication.getName())
                .orElseThrow(UserNotFoundException::new);

        if (folder.getFolderCertificates() == null) {
            folder.setFolderCertificates(new ArrayList<>());
        }

        for (Long certificateId : folderCertificatesDTO.getCertificateIds()) {
            CertificateInfoEntity certificateInfo = certificateInfoRepository.findById(certificateId)
                    .orElseThrow(() -> new InvalidCertificateException("유효하지 않은 인증서입니다."));

            if (!certificateInfo.getUser().getId().equals(user.getId())) {
                throw new InvalidCertificateException("인증서 소유자가 아닙니다.");
            }

            FolderCertificateEntity folderCertificate = FolderCertificateEntity.builder()
                    .folder(folder)
                    .certificate(certificateInfo)
                    .build();
            folderCertificateRepository.save(folderCertificate);
            folder.getFolderCertificates().add(folderCertificate);
        }

        folderRepository.save(folder);
    }

    public List<CertificateInfoEntity> getCertificatesInFolder(Long folderId, Authentication authenticatoin) {
        FolderEntity folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new InvalidFolderException());

        MemberEntity user = memberRepository.findByUsername(authenticatoin.getName())
                .orElseThrow(UserNotFoundException::new);

        if (!folder.getUser().getId().equals(user.getId())) {
            throw new InvalidFolderException();
        }

        return folder.getFolderCertificates().stream()
                .map(FolderCertificateEntity::getCertificate)
                .toList();
    }

    @Transactional
    public void sendCertificatesToVerifier(Long folderId, Long verifierId, Authentication authentication) {
        FolderEntity folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new InvalidFolderException());

        MemberEntity verifier = memberRepository.findById(verifierId)
                .orElseThrow(() -> new VerifierNotFoundException());

        MemberEntity user = memberRepository.findByUsername(authentication.getName())
                .orElseThrow(UserNotFoundException::new);

        if (!folder.getUser().getId().equals(user.getId())) {
            throw new InvalidFolderException();
        }

        SentCertificateEntity sentCertificate = SentCertificateEntity.builder()
                .folder(folder)
                .verifier(verifier)
                .build();
        folder.getSentCertificates().add(sentCertificate);
        verifier.getSentCertificates().add(sentCertificate);
        sentCertificateRepository.save(sentCertificate);
    }
}
