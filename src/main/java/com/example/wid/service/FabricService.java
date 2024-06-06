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

    public void run() throws GatewayException, CommitException {
        initLedger();
//        getAllAssets();
        getAllEntries();
//        createAsset();
//        createUnivClassAsset(assetId, "John Doe", "202312345", "2024/03/02~2024/06/21", "Blockchain Portfolio Management", "Capstone");
//        createCompetitionAsset();
        transferAssetAsync();
        readAssetById();
        updateNonExistentAsset();
    }

    public void getAll() throws GatewayException {
        System.out.println("==============GET ALL ASSETS==============");
        getAllEntries();
    }

    public void addAsset() throws EndorseException, CommitException, SubmitException, CommitStatusException {
//        createEncryptedUnivClass("did111", "33333333", "44444444");
//        createEncryptedCompetition("did222", "66666666", "55555555");
        createEncryptedAsset("did4", "77777777", "88888888", "교내 대회");
        String sample = "HJDUfpMpt+r3cSn/Asw2IorbStkrDk3OiMeiXRRWWC2+KV0gsQAgwL0E5b+/Rjk4GW4PWUKu49oCDQBgPG2ECDAxJVP3G9oTr36ShjGFzp4I3VyXtsIxYO14943Pklh0XsKuMpoDZdpTfupfOl2vyJv3El8l9mR4/onLwJhLeuaa6GPvFXfQ30TBzNzkUG53uUMlqi2rfNxzbq/taJMYtDuva83Jq96xgdPJjia4grXDrE8MJXo+C3OwtBuTc9oDyAsDUcsXj3iLfD0q/rhuwx7kfGPCj00V+0wEjUTmaQsWdMmyMRxsd+9twrQLejQ7lfneQR4jIuQIHNVgVMgFHg==";
//        createUnivClassAsset("did11", sample, "22", "dd", "af", "adf");

    }

    public void readUCAssetById() throws GatewayException {
        readEncryptedAssetById("did4");
    }


    private void initLedger() throws EndorseException, SubmitException, CommitStatusException, CommitException {
        System.out.println("\n--> Submit Transaction: InitLedger, function creates the initial set of assets on the ledger");
        contract.submitTransaction("InitLedger");
        System.out.println("*** Transaction committed successfully");
    }

    public void getAllEntries() throws GatewayException {
        System.out.println("\n--> Evaluate Transaction: GetAllEntries, function returns all the current assets on the ledger");

        var result = contract.evaluateTransaction("GetAllEntries");

        System.out.println("*** Result: " + prettyJson(result));
    }

//    public void getAllAssets() throws GatewayException {
//        System.out.println("GetAllAssets");
//        System.out.println("\n--> Evaluate Transaction: GetAllAssets, function returns all the current assets on the ledger");
//        var result = contract.evaluateTransaction("GetAllAssets");
//        System.out.println("*** Result: " + prettyJson(result));
//    }

    private String prettyJson(final byte[] json) {
        return prettyJson(new String(json, StandardCharsets.UTF_8));
    }

    private String prettyJson(final String json) {
        var parsedJson = JsonParser.parseString(json);
        return gson.toJson(parsedJson);
    }

    private void createAsset() throws EndorseException, SubmitException, CommitStatusException, CommitException {
        System.out.println("\n--> Submit Transaction: CreateAsset, creates new asset with ID, Color, Size, Owner and AppraisedValue arguments");
        contract.submitTransaction("CreateAsset", assetId, "yellow", "5", "Tom", "1300");
        System.out.println("*** Transaction committed successfully");
    }

    private void transferAssetAsync() throws EndorseException, SubmitException, CommitStatusException {
        System.out.println("\n--> Async Submit Transaction: TransferAsset, updates existing asset owner");
        var commit = contract.newProposal("TransferAsset")
                .addArguments(assetId, "Saptha")
                .build()
                .endorse()
                .submitAsync();
        var result = commit.getResult();
        var oldOwner = new String(result, StandardCharsets.UTF_8);
        System.out.println("*** Successfully submitted transaction to transfer ownership from " + oldOwner + " to Saptha");
        System.out.println("*** Waiting for transaction commit");
        var status = commit.getStatus();
        if (!status.isSuccessful()) {
            throw new RuntimeException("Transaction " + status.getTransactionId() + " failed to commit with status code " + status.getCode());
        }
        System.out.println("*** Transaction committed successfully");
    }

    private void readAssetById() throws GatewayException {
        System.out.println("\n--> Evaluate Transaction: ReadAsset, function returns asset attributes");
        var evaluateResult = contract.evaluateTransaction("ReadAsset", assetId);
        System.out.println("*** Result:" + prettyJson(evaluateResult));
    }

    private void updateNonExistentAsset() {
        try {
            System.out.println("\n--> Submit Transaction: UpdateAsset asset70, asset70 does not exist and should return an error");
            contract.submitTransaction("UpdateAsset", "asset70", "blue", "5", "Tomoko", "300");
            System.out.println("******** FAILED to return an error");
        } catch (EndorseException | SubmitException | CommitStatusException e) {
            System.out.println("*** Successfully caught the error: ");
            e.printStackTrace(System.out);
            System.out.println("Transaction ID: " + e.getTransactionId());
            var details = e.getDetails();
            if (!details.isEmpty()) {
                System.out.println("Error Details:");
                for (var detail : details) {
                    System.out.println("- address: " + detail.getAddress() + ", mspId: " + detail.getMspId() + ", message: " + detail.getMessage());
                }
            }
        } catch (CommitException e) {
            System.out.println("*** Successfully caught the error: " + e);
            e.printStackTrace(System.out);
            System.out.println("Transaction ID: " + e.getTransactionId());
            System.out.println("Status code: " + e.getCode());
        }
    }

    private void createUnivClassAsset(String assetId, String name, String studentID, String term, String summary, String subject) throws EndorseException, SubmitException, CommitStatusException, CommitException {
        System.out.println("\n--> Submit Transaction: CreateUnivClassAsset, creates new UnivClassAsset with did, name, studentID, term, summary, subject arguments");

        contract.submitTransaction("CreateUnivClassAsset", assetId, name, studentID, term, summary, subject);

        System.out.println("*** Transaction committed successfully");
    }

    private void createCompetitionAsset() throws EndorseException, SubmitException, CommitStatusException, CommitException {
        System.out.println("\n--> Submit Transaction: CreateCompetitionAsset, creates new CompetitionAsset with did, name, competitionName, achievement, organizer, summary arguments");

        contract.submitTransaction("CreateCompetitionAsset", "comp" + Instant.now().toEpochMilli(), "Jane Doe", "K-Hackathon", "1st place", "JBNU Software Engineering", "Blockchain-based service");

        System.out.println("*** Transaction committed successfully");
    }

    private void createEncryptedUnivClass(String assetId, String encryptedunivclass, String addedunivclass) throws EndorseException, SubmitException, CommitStatusException, CommitException {
        System.out.println("\n--> Submit Transaction: CreateEncryptedUnivClass, creates new EncryptedUnivClass with did, encryptedString");

        contract.submitTransaction("CreateEncryptedUnivClass", assetId, encryptedunivclass, addedunivclass);

        System.out.println("*** Transaction committed successfully");
    }

    private void createEncryptedCompetition(String assetId, String encryptedcompetition, String addedcompetition) throws EndorseException, SubmitException, CommitStatusException, CommitException {
        System.out.println("\n--> Submit Transaction: CreateEncryptedCompetition, creates new EncryptedCompetition with did, name, studentID, term, summary, subject arguments");

        contract.submitTransaction("CreateEncryptedUnivClass", assetId, encryptedcompetition, addedcompetition);

        System.out.println("*** Transaction committed successfully");
    }

    public void createEncryptedAsset(String assetId, String encryptedstring, String addedstring, String type) throws EndorseException, SubmitException, CommitStatusException, CommitException {
        System.out.println("\n--> Submit Transaction: CreateEncryptedCompetition, creates new EncryptedCompetition with did, name, studentID, term, summary, subject arguments");

        contract.submitTransaction("CreateEncryptedAsset", assetId, encryptedstring, addedstring, type);

        System.out.println("*** Transaction committed successfully");
    }

    private void readUnivClassAssetById(String assetId) throws GatewayException {
        System.out.println("\n--> Evaluate Transaction: ReadUnivClassAsset, function returns asset attributes for " + assetId);
        var evaluateResult = contract.evaluateTransaction("ReadUnivClassAsset", assetId);
        System.out.println("*** Result:" + prettyJson(evaluateResult));
    }

    private void readEncryptedUnivClassById(String assetId) throws GatewayException {
        System.out.println("\n--> Evaluate Transaction: ReadEncryptedUnivClass, function returns asset attributes for " + assetId);
        var evaluateResult = contract.evaluateTransaction("ReadEncryptedUnivClass", assetId);
        System.out.println("*** Result:" + prettyJson(evaluateResult));
    }

    private void readEncryptedCompetitionById(String assetId) throws GatewayException {
        System.out.println("\n--> Evaluate Transaction: ReadEncryptedCompetition, function returns asset attributes for " + assetId);
        var evaluateResult = contract.evaluateTransaction("ReadEncryptedCompetition", assetId);
        System.out.println("*** Result:" + prettyJson(evaluateResult));
    }

    private void readEncryptedAssetById(String assetId) throws GatewayException {
        System.out.println("\n--> Evaluate Transaction: ReadEncryptedAsset, function returns asset attributes for " + assetId);
        var evaluateResult = contract.evaluateTransaction("ReadEncryptedAsset", assetId);
        System.out.println("*** Result:" + prettyJson(evaluateResult));
    }

}
