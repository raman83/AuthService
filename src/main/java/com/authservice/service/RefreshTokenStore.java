package com.authservice.service;


import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class RefreshTokenStore {

    private final ConcurrentHashMap<String, String> refreshTokens = new ConcurrentHashMap<>();

    public void store(String refreshToken, String clientId) {
    	refreshTokens.put(refreshToken, clientId);
    }

    public String getClientId(String refreshToken) {
        return refreshTokens.get(refreshToken);
    }

    public void revoke(String refreshToken) {
    	refreshTokens.remove(refreshToken);
    }
}
