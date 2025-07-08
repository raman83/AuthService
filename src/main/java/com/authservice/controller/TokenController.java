package com.authservice.controller;


import lombok.RequiredArgsConstructor;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.authcore.dto.TokenRequest;
import com.authcore.dto.TokenResponse;
import com.authcore.service.JwtTokenService;
import com.authservice.service.RefreshTokenStore;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class TokenController {
    

    private final JwtTokenService jwtTokenService;

    private final  RefreshTokenStore refreshTokenStore;
    
  
    
    
    @PostMapping("/introspect")
    public ResponseEntity<Map<String, Object>> introspect(@RequestParam("token") String token) {
        Map<String, Object> response = new HashMap<>();

        try {
            SignedJWT jwt = SignedJWT.parse(token);
            boolean isValid = jwtTokenService.validate(token.toString());

            response.put("active", isValid);

            if (isValid) {
                Map<String, Object> claims = jwt.getJWTClaimsSet().getClaims();
                response.putAll(claims);
            }
        } catch (ParseException e) {
            response.put("active", false);
        }

        return ResponseEntity.ok(response);
    }


    @PostMapping("/token")
    public ResponseEntity<?> generateAccessToken(@RequestBody TokenRequest request) throws JOSEException {
        String grantType = request.getGrantType();
        TokenResponse tokenResponse =null;

        if("client_credentials".equals(grantType)){
         tokenResponse = jwtTokenService.generateToken(request.getClientId(),request.getUserId(),"ROLE_SERVICE");
        refreshTokenStore.store( tokenResponse.getRefreshToken(), request.getClientId());
        }
        else if ("refresh_token".equals(grantType)) {
            String refreshToken = request.getRefreshToken();
            
            String clientId = refreshTokenStore.getClientId(refreshToken);

            if (clientId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
            }

             tokenResponse = jwtTokenService.generateToken(clientId,request.getUserId(),"ROLE_CUSTOMER"); // sign new JWT
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
