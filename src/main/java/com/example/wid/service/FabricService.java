package com.example.wid.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.hyperledger.fabric.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

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
        getAllAssets();
        createAsset();
        transferAssetAsync();
        readAssetById();
        updateNonExistentAsset();
    }

    private void initLedger() throws EndorseException, SubmitException, CommitStatusException, CommitException {
        System.out.println("\n--> Submit Transaction: InitLedger, function creates the initial set of assets on the ledger");
        contract.submitTransaction("InitLedger");
        System.out.println("*** Transaction committed successfully");
    }

    private void getAllAssets() throws GatewayException {
        System.out.println("GetAllAssets");
        System.out.println("\n--> Evaluate Transaction: GetAllAssets, function returns all the current assets on the ledger");
        var result = contract.evaluateTransaction("GetAllAssets");
        System.out.println("*** Result: " + prettyJson(result));
    }

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
}