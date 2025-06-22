package com.authservice.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.authservice.dto.TokenResponse;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

@Service
public class JwtTokenService {

//	private static final String SECRET="uH7pN2rPeT9vXrWqz8GxVrWbZy9sUwFg4XpEm6nBtOw";
   // private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());
//Token validity in milliseconds (e.g., 15 minutes)
 private static final long ACCESS_TOKEN_VALIDITY = 1 * 60 * 60 * 1000;
 // Refresh token validity (e.g., 7 days)
 private static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000;
 
 
 private final RSAKey rsaKey;

 public JwtTokenService(RSAKey rsaKey) {
     this.rsaKey = rsaKey;
 }

 public TokenResponse generateToken(String clientId) throws JOSEException {
     long now = System.currentTimeMillis();

     // Build access token
     
     SignedJWT signedJWT = new SignedJWT(
    		    new JWSHeader.Builder(JWSAlgorithm.RS256)
    		        .keyID(rsaKey.getKeyID()) // must match JWK
    		        .build(),
    		    new JWTClaimsSet.Builder()
    		        .subject(clientId)
    		        .jwtID(UUID.randomUUID().toString())
    		        .issueTime(new Date(now))
    		        .expirationTime(new Date(now + ACCESS_TOKEN_VALIDITY))
    		        .build()
    		);

    		signedJWT.sign(new RSASSASigner(rsaKey.toPrivateKey()));
    		String accessToken = signedJWT.serialize();
     
     
     
    		
    		
    		JWTClaimsSet claims = new JWTClaimsSet.Builder()
    			    .subject(clientId)
    			    .claim("type", "refresh")
    			    .issueTime(new Date(now))
    			    .expirationTime(new Date(now + REFRESH_TOKEN_VALIDITY))
    			    .jwtID(UUID.randomUUID().toString())
    			    .build();

    			JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
    			    .keyID(rsaKey.getKeyID()) // must match your exposed JWKS key
    			    .build();

    			SignedJWT signedJWT2 = new SignedJWT(header, claims);
    			signedJWT2.sign(new RSASSASigner(rsaKey.toPrivateKey()));

    			String refreshToken = signedJWT2.serialize();
    		
    	

     return new TokenResponse(accessToken, refreshToken, ACCESS_TOKEN_VALIDITY / 1000);
 }

 public String generateRefreshToken() {
     return UUID.randomUUID().toString();
 }


 public long getAccessTokenValidityMillis() {
     return ACCESS_TOKEN_VALIDITY;
 }

 public long getRefreshTokenValidityMillis() {
     return REFRESH_TOKEN_VALIDITY;
 }
 
} 

