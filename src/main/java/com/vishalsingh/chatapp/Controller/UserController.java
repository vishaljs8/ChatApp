package com.vishalsingh.chatapp.Controller;

import com.vishalsingh.chatapp.Entity.UserEntity;
import com.vishalsingh.chatapp.Models.AuthRequest;
import com.vishalsingh.chatapp.Models.AuthResponse;
import com.vishalsingh.chatapp.Services.UserServices;
import com.vishalsingh.chatapp.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserServices userServices;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/create")
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userServices.save(user);
    }

    @GetMapping("/getUsers")
    public List<UserEntity> getUsers(Authentication authentication) {

        String currentUser = authentication.getName();

        return userServices.getUsers()
                .stream()
                .filter(user ->
                        !user.getUsername().equals(currentUser))
                .toList();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {

            String username = request.getUsername()
                    .trim()
                    .toLowerCase();

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            request.getPassword()
                    )
            );

            UserDetails user = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(user.getUsername());

            return ResponseEntity.ok(new AuthResponse(token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }
}
