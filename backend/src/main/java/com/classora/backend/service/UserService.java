package com.classora.backend.service;

import com.classora.backend.entity.Role;
import com.classora.backend.entity.User;
import com.classora.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.classora.backend.dto.LoginRequest;
import com.classora.backend.dto.LoginResponse;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createAdmin(String email, String password) {
        User user = new User();
        user.setEmail(email);

        // 🔐 PASSWORD ENCRYPT HERE
        user.setPassword(passwordEncoder.encode(password));

        user.setRole(Role.ADMIN);
        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        boolean isPasswordMatch =
                passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!isPasswordMatch) {
            throw new RuntimeException("Invalid password");
        }

        return new LoginResponse(
                "Login successful",
                user.getEmail(),
                user.getRole().name()
        );
    }


}
