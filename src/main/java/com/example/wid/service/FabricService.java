package com.example.wid.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.hyperledger.fabric.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

@Service
public class FabricService {
    private final Contract contract;
    private final String assetId = "asset" + Instant.now().toEpochMilli();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    public FabricService(Gateway gateway) {
        var network = gateway.getNetwork("mychannel");
        contract = network.getContract("basic");
    }
    public void readUCAssetById(Long id) throws GatewayException {
        String index = "did" + id;
        readEncryptedAssetById(index);
    }

    public void initLedger() throws EndorseException, SubmitException, CommitStatusException, CommitException {
        System.out.println("\n--> Submit Transaction: InitLedger, function creates the initial set of assets on the ledger");
        contract.submitTransaction("InitLedger");
        System.out.println("*** Transaction committed successfully");
    }

    public void getAllEntries() throws GatewayException {
        System.out.println("\n--> Evaluate Transaction: GetAllEntries, function returns all the current assets on the ledger");

        var result = contract.evaluateTransaction("GetAllEntries");

        System.out.println("*** Result: " + prettyJson(result));
    }

    private String prettyJson(final byte[] json) {
        return prettyJson(new String(json, StandardCharsets.UTF_8));
    }

    private String prettyJson(final String json) {
        var parsedJson = JsonParser.parseString(json);
        return gson.toJson(parsedJson);
    }

    public void createEncryptedAsset(String assetId, String encryptedstring, String addedstring, String type) throws EndorseException, SubmitException, CommitStatusException, CommitException {
        System.out.println("\n--> Submit Transaction: CreateEncryptedCompetition, creates new EncryptedCompetition with did, name, studentID, term, summary, subject arguments");

        contract.submitTransaction("CreateEncryptedAsset", assetId, encryptedstring, addedstring, type);

        System.out.println("*** Transaction committed successfully");
    }

    private void readEncryptedAssetById(String assetId) throws GatewayException {
        System.out.println("\n--> Evaluate Transaction: ReadEncryptedAsset, function returns asset attributes for " + assetId);
        var evaluateResult = contract.evaluateTransaction("ReadEncryptedAsset", assetId);
        System.out.println("*** Result:" + prettyJson(evaluateResult));
    }

}
