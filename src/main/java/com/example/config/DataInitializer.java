package com.example.config;

import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            Role userRole = new Role();
            userRole.setName("USER");

            roleRepository.save(adminRole);
            roleRepository.save(userRole);

            User alvaro = new User();
            alvaro.setUsername("alvaro");
            alvaro.setPassword(passwordEncoder.encode("alvaro"));
            alvaro.setRoles(Set.of(adminRole));

            User pedro = new User();
            pedro.setUsername("pedro");
            pedro.setPassword(passwordEncoder.encode("pedro"));
            pedro.setRoles(Set.of(userRole));

            userRepository.save(alvaro);
            userRepository.save(pedro);
        }
    }
}



