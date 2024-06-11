package com.example.wid.service;

import com.example.wid.controller.exception.UserNotFoundException;
import com.example.wid.entity.CertificateInfoEntity;
import com.example.wid.entity.MemberEntity;
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

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

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

    public void addNewAssets(Map<String, String> certificateMap) throws EndorseException, CommitException, SubmitException, CommitStatusException {
        contract.submitTransaction("CreateEncryptedAsset"
                , certificateMap.get("assetId")
                , certificateMap.get("encryptedstring")
                , certificateMap.get("addedstring")
                , certificateMap.get("type")
        );
    }
    
    // 서명된 증명서 정보만 가져오기
    public List<Map<String, String>> getAllCertificates(Authentication authentication) throws GatewayException, NoSuchAlgorithmException, InvalidKeySpecException, JsonProcessingException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        MemberEntity user = null;
        if(memberRepository.findByUsername(authentication.getName()).isPresent()) {
            user = memberRepository.findByUsername(authentication.getName()).get();
        } else throw new UserNotFoundException();

        List<CertificateInfoEntity> certificateInfoEntities = user.getUserCertificates();
        List<Map<String, String>> certificateJsons = new ArrayList<>();
        for(CertificateInfoEntity certificateInfoEntity : certificateInfoEntities) {
            if(certificateInfoEntity.getIsSigned()==false) continue;
            String encodedIssuerPrivateKey = certificateInfoEntity.getIssuer().getPrivateKey();
            String encodedUserPublicKey = certificateInfoEntity.getUser().getPublicKey();

            PrivateKey issuerPrivateKey = KeyFactory
                    .getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(Base64
                            .getDecoder()
                            .decode(encodedIssuerPrivateKey.getBytes())
                    ));
            PublicKey userPublicKey = KeyFactory
                    .getInstance("RSA")
                    .generatePublic(new PKCS8EncodedKeySpec(Base64
                            .getDecoder()
                            .decode(encodedUserPublicKey.getBytes())
                    ));

            Long id = certificateInfoEntity.getId();
            String didId = "did" + id;
            String encryptedCertificate = readEncryptedCertificate(didId);
            Map<String, String> encryptMap = objectMapper.readValue(encryptedCertificate, Map.class);
            byte[] encryptedStrings = Base64.getDecoder().decode(encryptMap.get("encryptedstring"));
            byte[] decryptByUser = decrypt(encryptedStrings,userPublicKey);

            byte[] originalData = new byte[decryptByUser.length + 20];
            byte[] removedData = Base64.getDecoder().decode(encryptMap.get("addedstring"));
            System.arraycopy(removedData, 0, originalData, 0, 20);
            System.arraycopy(decryptByUser, 0, originalData, 20, decryptByUser.length);

            byte[] decryptByIssuer = decrypt(originalData, issuerPrivateKey);
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
