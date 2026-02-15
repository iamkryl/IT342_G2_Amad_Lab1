package amad.userauth.controller;

import amad.userauth.model.User;
import amad.userauth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Register a new user
     * Matches diagram: registerUser(User user): void
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password
    ) {
        try {
            User user = authService.registerUser(firstName, lastName, email, password);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Login user
     * Matches diagram: loginUser(String email, String password): String
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestParam String email,
            @RequestParam String password
    ) {
        try {
            // Get token from service
            String token = authService.login(email, password);

            // Get user info by token
            User user = authService.getUserByToken(token);

            // Create response
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("email", user.getEmail());
            response.put("firstName", user.getFirstName());
            response.put("lastName", user.getLastName());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Logout user
     * Matches diagram: logoutUser(String token): void
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestParam String token) {
        try {
            authService.logout(token);
            return ResponseEntity.ok("Logged out successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get current user profile
     * Matches diagram: getUserProfile(String token): User
     */
    @GetMapping("/user/me")
    public ResponseEntity<?> getUserProfile(@RequestParam String token) {
        try {
            User user = authService.getUserByToken(token);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}