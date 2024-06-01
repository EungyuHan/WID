package com.example.wid.controller;

import com.example.wid.service.FabricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
}
