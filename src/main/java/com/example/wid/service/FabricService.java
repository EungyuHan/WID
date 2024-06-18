package com.example.wid.service;

import com.example.wid.controller.exception.UserNotFoundException;
import com.example.wid.entity.*;
import com.example.wid.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.hyperledger.fabric.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.stream.Stream;

@Service
public class FabricService {
    private final Contract contract;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    public FabricService(Gateway gateway, MemberRepository memberRepository, ObjectMapper objectMapper) {
        this.memberRepository = memberRepository;
        this.objectMapper = objectMapper;
        var network = gateway.getNetwork("mychannel");
        contract = network.getContract("basic");
    }

    // FabricTestController
    public void readUCAssetById(Long id) throws GatewayException {
        String index = "did" + id;
        readEncryptedAssetById(index);
    }

    public void initLedger() throws EndorseException, SubmitException, CommitStatusException, CommitException {
        contract.submitTransaction("InitLedger");
    }

    public void getAllEntries() throws GatewayException {
        System.out.println("\n--> Evaluate Transaction: GetAllEntries, function returns all the current assets on the ledger");

        var result = contract.evaluateTransaction("GetAllEntries");

        System.out.println("*** Result: " + prettyJson(result));
    }


    // CertificateController
    public void addNewAssets(Map<String, String> certificateMap) throws EndorseException, CommitException, SubmitException, CommitStatusException {
        contract.submitTransaction("CreateEncryptedAsset"
                , certificateMap.get("assetId")
                , certificateMap.get("encryptedstring")
                , certificateMap.get("addedstring")
                , certificateMap.get("type")
        );
    }

    // 서명된 증명서 정보만 가져오기
    @Transactional
    public List<Map<String, String>> getAllUserCertificates(Authentication authentication) throws GatewayException, NoSuchAlgorithmException, InvalidKeySpecException, JsonProcessingException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        MemberEntity user = memberRepository.findByUsername(authentication.getName())
                .orElseThrow(UserNotFoundException::new);

        List<CertificateInfoEntity> certificateInfoEntities = user.getUserCertificates();
        return getCertificateJsonList(certificateInfoEntities);
    }

    public List<Map<String, String>> getAllVerifierCertificates(Authentication authentication, Long sentCertificateId) throws GatewayException, NoSuchAlgorithmException, InvalidKeySpecException, JsonProcessingException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        MemberEntity verifier = memberRepository.findByUsername(authentication.getName())
                .orElseThrow(UserNotFoundException::new);

        SentCertificateEntity sentCertificateEntity = verifier.getSentCertificates().stream()
                .filter(sentCertificate -> sentCertificate.getId().equals(sentCertificateId))
                .findAny()
                .get();

        Stream<FolderCertificateEntity> stream = sentCertificateEntity.getFolder().getFolderCertificates().stream();
        List<CertificateInfoEntity> certificateInfoEntities = new ArrayList<>();
        sentCertificateEntity.getFolder().getFolderCertificates().stream()
                .forEach(folderCertificateEntity -> certificateInfoEntities.add(folderCertificateEntity.getCertificate()));
        return getCertificateJsonList(certificateInfoEntities);
    }

    // 내부메소드
    private List<Map<String, String>> getCertificateJsonList(List<CertificateInfoEntity> certificateInfoEntities) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, JsonProcessingException, GatewayException {
        List<Map<String, String>> certificateJsons = new ArrayList<>();
        for(CertificateInfoEntity certificateInfoEntity : certificateInfoEntities){
            if(!certificateInfoEntity.getIsSigned()) continue;
            String encodedUserPriavateKey = certificateInfoEntity.getUser().getPrivateKey();
            String encodedIssuerPublicKey = certificateInfoEntity.getIssuer().getPublicKey();

            PrivateKey userPrivateKey = KeyFactory
                    .getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(Base64
                            .getDecoder()
                            .decode(encodedUserPriavateKey.getBytes())
                    ));
            PublicKey issuerPublicKey = KeyFactory
                    .getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(Base64
                            .getDecoder()
                            .decode(encodedIssuerPublicKey.getBytes())
                    ));
            System.out.println("===============================");
            Long id = certificateInfoEntity.getId();
            String didId = "did" + id;
            String encryptedCertificate = readEncryptedCertificate(didId);
            Map<String, String> encryptMap = objectMapper.readValue(encryptedCertificate, Map.class);
            byte[] encryptedStrings = Base64.getDecoder().decode(encryptMap.get("encryptedstring"));
            System.out.println("블록체인 2차 암호화 값 : " + encryptMap.get("encryptedstring"));
            byte[] decryptByUser = decrypt(encryptedStrings,userPrivateKey);
            System.out.println("1차 복호화 값 : " + Base64.getEncoder().encodeToString(decryptByUser));

            byte[] originalData = new byte[decryptByUser.length + 20];
            byte[] removedData = Base64.getDecoder().decode(encryptMap.get("addedstring"));
            System.arraycopy(removedData, 0, originalData, 0, 20);
            System.arraycopy(decryptByUser, 0, originalData, 20, decryptByUser.length);
            System.out.println("블록체인 - 제거된 데이터 : " + encryptMap.get("addedstring"));
            System.out.println("합쳐진 데이터 : " + Base64.getEncoder().encodeToString(originalData));

            byte[] decryptByIssuer = decrypt(originalData, issuerPublicKey);
            System.out.println(new String(decryptByIssuer));
            Map<String, String> certificateJson = objectMapper.readValue(new String(decryptByIssuer), Map.class);
            certificateJson.put("id", id.toString());

            certificateJsons.add(certificateJson);
        }
        return certificateJsons;
    }
    private String readEncryptedCertificate(String assetId) throws GatewayException {
        var evaluateResult = contract.evaluateTransaction("ReadEncryptedAsset", assetId);
        return prettyJson(evaluateResult);
    }

    private void readEncryptedAssetById(String assetId) throws GatewayException {
        System.out.println("\n--> Evaluate Transaction: ReadEncryptedAsset, function returns asset attributes for " + assetId);
        var evaluateResult = contract.evaluateTransaction("ReadEncryptedAsset", assetId);
        prettyJson(evaluateResult);
        System.out.println("*** Result:" + prettyJson(evaluateResult));
    }

    private String prettyJson(final byte[] json) {
        return prettyJson(new String(json, StandardCharsets.UTF_8));
    }

    private String prettyJson(final String json) {
        var parsedJson = JsonParser.parseString(json);
        return gson.toJson(parsedJson);
    }

    public byte[] decrypt(byte[] encryptedData, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encryptedData);
    }
}