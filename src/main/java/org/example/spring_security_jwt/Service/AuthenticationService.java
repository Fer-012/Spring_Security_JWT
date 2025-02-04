package org.example.spring_security_jwt.Service;


import org.example.spring_security_jwt.Repository.UserRepository;
import org.example.spring_security_jwt.Request.LoginRequest;
import org.example.spring_security_jwt.Request.SignupRequest;
import org.example.spring_security_jwt.models.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(SignupRequest input) {
        if (userRepository.findByUsername(input.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        User user = new User();
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setRoles(input.getRoles());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        User savedUser = userRepository.save(user);
        return savedUser;
    }
    public User authenticate(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        return userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
