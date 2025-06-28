package com.authservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.authservice.dto.LoginRequest;
import com.authservice.dto.TokenResponse;
import com.authservice.model.CustomerCredential;
import com.authservice.repository.CustomerCredentialRepository;
import com.nimbusds.jose.JOSEException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
	
	
	private final CustomerCredentialRepository repository;
	private final JwtTokenService jwtService;
	
	
	
	public TokenResponse login(LoginRequest request) throws JOSEException {
		CustomerCredential creds = repository.findByEmail(request.getEmail())
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    if (!request.getPassword().equals(creds.getPasswordHash())) {
	        throw new RuntimeException("Invalid credentials");
	    }

	    TokenResponse token = jwtService.generateToken(creds.getCustomerId(), creds.getRole());
	    return token;
	}

}
