package com.authservice.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.authservice.dto.RefreshTokenRequest;
import com.authservice.dto.TerminalCredentials;
import com.authservice.dto.TokenRequest;
import com.authservice.dto.TokenResponse;
import com.authservice.service.JwtTokenService;
import com.authservice.service.RefreshTokenStore;
import com.authservice.service.TerminalRegistryService;
import com.nimbusds.jose.JOSEException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class TokenController {


    private final TerminalRegistryService terminalRegistryService;
    

    private final JwtTokenService jwtTokenService;

    private final  RefreshTokenStore refreshTokenStore;
    
  


    @PostMapping("/token")
    public ResponseEntity<?> generateAccessToken(@RequestBody TokenRequest request) throws JOSEException {
        String grantType = request.getGrantType();
        TokenResponse tokenResponse =null;

        if("client_credentials".equals(grantType)){
    	
    	boolean valid = terminalRegistryService.validateClient(request.getClientId(), request.getClientSecret());

        if (!valid) {
            return ResponseEntity.status(401).build();
        }

         tokenResponse = jwtTokenService.generateToken(request.getClientId());
        refreshTokenStore.store( tokenResponse.getRefreshToken(), request.getClientId());
        }
        else if ("refresh_token".equals(grantType)) {
            String refreshToken = request.getRefreshToken();
            
            String clientId = refreshTokenStore.getClientId(refreshToken);

            if (clientId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
            }

             tokenResponse = jwtTokenService.generateToken(clientId); // sign new JWT
             if (refreshTokenStore == null) {
            	    throw new IllegalStateException("refreshTokens map is not initialized");
            	}
             
             else {
            	 System.out.println("refreshToken" + tokenResponse.getRefreshToken() + "clinet ID " + request.getClientId());
             }
             refreshTokenStore.store( tokenResponse.getRefreshToken(), clientId);

        }
        return ResponseEntity.ok(tokenResponse);
    }

} 
