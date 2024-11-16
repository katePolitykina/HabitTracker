package com.example.usermanagerservise.controller;

import com.example.usermanagerservise.DTO.AuthResponseDTO;
import com.example.usermanagerservise.DTO.LoginDTO;
import com.example.usermanagerservise.DTO.RegisterDTO;
import com.example.usermanagerservise.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
