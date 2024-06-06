package com.example.wid.controller;

import com.example.wid.service.FabricService;
import org.hyperledger.fabric.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/did")
public class FabricTestController {

    private final FabricService fabricService;

    @Autowired
    public FabricTestController(FabricService fabricService) {
        this.fabricService = fabricService;
    }

    @GetMapping("/blockchain")
    public String runBlockchainOperations() throws EndorseException, CommitException, SubmitException, CommitStatusException {
        fabricService.initLedger();
        return "Init Ledger";
    }

    @GetMapping("/getAllAssets")
    public String getAllAssets() throws GatewayException {
        fabricService.getAllEntries();
        return "Get All Assets Complete";
    }

    @GetMapping("/addNewAssets")
    public String addNewAssets() throws EndorseException, CommitException, SubmitException, CommitStatusException {
//        fabricService.addAsset();
        return "New Assets Added";
    }

    @PostMapping("/readEncryptedAssets")
    public String readdUCAssets(@RequestParam("didId") Long didId) throws GatewayException {
//        Long parsedLong = Long.parseLong(didId);
        fabricService.readUCAssetById(didId);
        return "read UnivClass Assets";
    }
}
