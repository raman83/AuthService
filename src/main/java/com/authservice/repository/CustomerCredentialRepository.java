package com.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authservice.model.CustomerCredential;

public interface CustomerCredentialRepository extends JpaRepository<CustomerCredential, Long> {
    Optional<CustomerCredential> findByEmail(String email);
}