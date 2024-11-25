package com.example.usermanagerservise.service;

import com.example.usermanagerservise.DTO.AuthResponseDTO;
import com.example.usermanagerservise.DTO.LoginDTO;
import com.example.usermanagerservise.DTO.RegisterDTO;
import com.example.usermanagerservise.configuration.security.JWTGenerator;
import com.example.usermanagerservise.entity.Role;
import com.example.usermanagerservise.entity.UserEntity;
import com.example.usermanagerservise.repository.RoleRepository;
import com.example.usermanagerservise.repository.UserRepository;
import com.example.usermanagerservise.service.MessageSender;
import lombok.AllArgsConstructor;
import org.example.RabbitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import java.util.Collections;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;
    private MessageSender messageSender;
    public static boolean isValidEmail(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
            return true;
        } catch (AddressException ex) {
            return false;
        }
    }


    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        if(userRepository.existsByEmail(registerDTO.getEmail())){
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }

        if(isValidEmail(registerDTO.getEmail())){
            return new ResponseEntity<>("Couldn't validate email", HttpStatus.BAD_REQUEST);
        }


        UserEntity user = new UserEntity();
        user.setUsername(registerDTO.getUsername());
        user.setPasswordHash(passwordEncoder.encode((registerDTO.getPassword())));

        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        user.setEmail(registerDTO.getEmail());
        userRepository.save(user);

        RabbitDTO rabbitDTO = new RabbitDTO();
        rabbitDTO.setAccessToken(GenerateToken(registerDTO.getEmail(), registerDTO.getPassword()));
        rabbitDTO.setUserEmail(registerDTO.getEmail());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String url = attributes.getRequest().getRequestURL().toString();

        rabbitDTO.setUrl(url);
        messageSender.send(rabbitDTO);

        return new ResponseEntity<>("User registered success", HttpStatus.OK );
    }

    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO){
        String token = GenerateToken(loginDTO.getEmail(), loginDTO.getPassword());
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }
    private String GenerateToken(String email, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,
                        password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtGenerator.generateToken(authentication);
    }
}
