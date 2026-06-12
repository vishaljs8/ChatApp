package com.vishalsingh.chatapp.Services;

import com.vishalsingh.chatapp.Entity.UserEntity;
import com.vishalsingh.chatapp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity save(UserEntity user) {
        String username = user.getUsername();

        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("Username is required");
        }


        username = username.trim().toLowerCase();

        user.setUsername(username);


        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }
}
