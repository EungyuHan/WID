package com.example.wid.controller;

import com.example.wid.service.FabricService;
import org.hyperledger.fabric.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/did")
public class FabricTestController {

    private final FabricService fabricService;

    @Autowired
    public FabricTestController(FabricService fabricService) {
        this.fabricService = fabricService;
    }

    @GetMapping("/blockchain")
    public String runBlockchainOperations() {
        try {
            fabricService.run();
            return "Blockchain operations completed successfully.";
        } catch (Exception e) {
            return "Error during blockchain operations: " + e.getMessage();
        }
    }

    @GetMapping("/getAllAssets")
    public String getAllAssets() throws GatewayException {
        fabricService.getAllEntries();
        return "Get All Assets Complete";
    }

    @GetMapping("/addNewAssets")
    public String addNewAssets() throws EndorseException, CommitException, SubmitException, CommitStatusException {
        fabricService.addAsset();
        return "New Assets Added";
    }

    @GetMapping("/readUCAssets")
    public String readdUCAssets() throws GatewayException {
        fabricService.readUCAssetById();
        return "read UnivClass Assets";
    }

}
