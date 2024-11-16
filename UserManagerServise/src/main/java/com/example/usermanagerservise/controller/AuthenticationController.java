package com.example.usermanagerservise.controller;

import com.example.usermanagerservise.DTO.AuthResponseDTO;
import com.example.usermanagerservise.DTO.LoginDTO;
import com.example.usermanagerservise.DTO.RegisterDTO;
import com.example.usermanagerservise.configuration.security.JWTGenerator;
import com.example.usermanagerservise.entity.Role;
import com.example.usermanagerservise.entity.UserEntity;
import com.example.usermanagerservise.repository.RoleRepository;
import com.example.usermanagerservise.repository.UserRepository;
import com.example.usermanagerservise.service.AuthenticationService;
import com.example.usermanagerservise.service.MessageSender;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@AllArgsConstructor
@RequestMapping("/HabitTracker/auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        return authenticationService.register(registerDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO){
        return authenticationService.login( loginDTO);
    }
}
