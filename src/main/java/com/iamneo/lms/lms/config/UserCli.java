package com.iamneo.lms.lms.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.iamneo.lms.lms.enumerated.Role;
import com.iamneo.lms.lms.model.User;
import com.iamneo.lms.lms.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserCli implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() > 0) return;

        var admin = User.builder()
                        .userId("admin@gmail.com")
                        .password(passwordEncoder.encode("Admin@123"))
                        .role(Role.ADMIN)
                        .build();
        userRepository.save(admin);
    }

    
}
