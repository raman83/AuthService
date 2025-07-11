package com.authservice.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class JwkSetController {

    private final RSAKey rsaKey;

   

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> getKeys() {
        return new JWKSet(rsaKey).toJSONObject();
    }
}