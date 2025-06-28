package com.authservice.service;
import java.util.List;

public class ScopeMappingService {

    public  static List<String> getScopesForRole(String role) {
        return switch (role) {
            case "ROLE_CUSTOMER" -> List.of(
                "fdx:accounts.read", "fdx:accounts.write",
                "fdx:transactions.read", "fdx:transactions.write",
                "fdx:customers.read", "fdx:customers.write"

            );
            case "ROLE_SERVICE" -> List.of(
                "fdx:accounts.read"
            );
            case "ROLE_VIEWER" -> List.of("fdx:accounts.read");
            default -> List.of();
        };
    }
}
