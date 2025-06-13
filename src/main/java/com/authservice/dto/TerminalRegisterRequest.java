package com.authservice.dto;

import lombok.Data;

@Data
public class TerminalRegisterRequest {
    private String terminalId;
    private String ksn;
    private String encryptedCode;
}
