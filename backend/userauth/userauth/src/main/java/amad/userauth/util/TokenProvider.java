package amad.userauth.util;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class TokenProvider {

    private String jwtSecret = "mySecretKey123";
    private Long jwtExpiration = 86400000L; // 24 hours in milliseconds

    // Store active tokens (in-memory for simplicity)
    private Map<String, Long> activeTokens = new HashMap<>();

    /**
     * Generate a token for the user
     */
    public String generateToken(Long userId) {
        // For Lab 1, we use a simple token format
        // In production, this would be a proper JWT
        String token = "token-" + userId + "-" + UUID.randomUUID().toString();
        activeTokens.put(token, userId);
        return token;
    }

    public boolean validateToken(String token) {
        return activeTokens.containsKey(token);
    }

    public Long getUserIdFromToken(String token) {
        return activeTokens.get(token);
    }

    public void revokeToken(String token) {
        activeTokens.remove(token);
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public Long getJwtExpiration() {
        return jwtExpiration;
    }
}
