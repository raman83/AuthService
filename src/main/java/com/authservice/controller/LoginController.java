package com.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.dto.LoginRequest;
import com.authservice.dto.TokenResponse;
import com.authservice.service.LoginService;
import com.nimbusds.jose.JOSEException;

@RestController
@RequestMapping("/api/v1")
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	
	@PostMapping("/auth/login")
	public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) throws JOSEException {
	    return ResponseEntity.ok(loginService.login(request));
	}

}
