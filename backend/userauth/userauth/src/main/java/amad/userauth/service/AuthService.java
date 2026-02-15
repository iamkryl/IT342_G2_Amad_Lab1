package amad.userauth.service;

import amad.userauth.model.User;
import amad.userauth.repository.UserRepository;
import amad.userauth.util.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Register a new user
     * Matches diagram: registerUser(User user): User
     */
    public User registerUser(String firstName, String lastName, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);
        user.setPasswordHash(hashedPassword);

        return userRepository.save(user);
    }

    /**
     * Login user and return token
     * Matches diagram: login(String email, String password): String
     */
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Generate and return token
        return tokenProvider.generateToken(user.getUserId());
    }

    /**
     * Logout user by revoking token
     * Matches diagram: logout(String token): void
     */
    public void logout(String token) {
        tokenProvider.revokeToken(token);
    }

    /**
     * Validate if token is valid
     * Matches diagram: validateToken(String token): boolean
     */
    public boolean validateToken(String token) {
        return tokenProvider.validateToken(token);
    }

    /**
     * Get user by ID
     * Matches diagram: getUserById(int user_id): User
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Get user by token
     */
    public User getUserByToken(String token) {
        if (!validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }
        Long userId = tokenProvider.getUserIdFromToken(token);
        return getUserById(userId);
    }
}