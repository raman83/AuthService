package com.authservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.dto.TerminalCredentials;
import com.authservice.dto.TerminalRegisterRequest;
import com.authservice.service.TerminalRegistryService;



@RestController
@RequestMapping("/api/v1")
public class TerminalRegistrationController {

    private final TerminalRegistryService terminalRegistryService;

    public TerminalRegistrationController(TerminalRegistryService terminalRegistryService) {
        this.terminalRegistryService = terminalRegistryService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerTerminal(@RequestBody TerminalRegisterRequest request) {
        boolean isValid = terminalRegistryService.validateAndRegister(request);
        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid registration code.");
        }

        TerminalCredentials credentials = terminalRegistryService.generateClientCredentials(request.getTerminalId());
        return ResponseEntity.ok(credentials);
    }
}
