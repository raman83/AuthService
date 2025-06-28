package com.authservice.service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authservice.dto.CustomerRegistrationRequest;
import com.authservice.dto.TerminalCredentials;
import com.authservice.dto.TerminalRegisterRequest;
import com.authservice.model.CustomerCredential;
import com.authservice.repository.CustomerCredentialRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerRegistryService {
	
	private final CustomerCredentialRepository repository;

	
	public void registerCustomer(CustomerRegistrationRequest request) {
		CustomerCredential entity = new CustomerCredential();
	    entity.setEmail(request.getEmail());
	    entity.setPasswordHash(request.getPassword());
	    entity.setCustomerId(request.getCustomerId());
	    entity.setRole(request.getRole());
	    repository.save(entity);
	}

	
	
}
