package com.BE.service;


import com.BE.model.response.AuthenticationResponse;
import com.BE.model.entity.User;
import com.BE.model.request.AuthenticationRequest;
import com.BE.model.request.LoginRequestDTO;
import com.BE.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthenticationService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JWTService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    public AuthenticationResponse register(AuthenticationRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user = userRepository.save(user);
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.
                builder()
                .role(request.getRole())
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .username(request.getUsername())
                .token(token).build();
    }
    public AuthenticationResponse authenticate(LoginRequestDTO request){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        }catch (Exception e) {
            throw new NullPointerException("Wrong Id Or Password") ;
        }
        User user =userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.
                builder()
                .role(user.getRole())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .username(user.getUsername())
                .token(token).build();
    }

}

