package amad.userauth.util;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class TokenProvider {

    private String jwtSecret = "mySecretKey123";
    private Long jwtExpiration = 86400000L;

    private Map<String, Long> activeTokens = new HashMap<>();

    public String generateToken(Long userId) {
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
}