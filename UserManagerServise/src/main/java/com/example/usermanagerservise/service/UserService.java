package com.example.usermanagerservise.service;
import com.example.usermanagerservise.DTO.UserDTO;
import com.example.usermanagerservise.entity.UserEntity;
import com.example.usermanagerservise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(String username, String email, String passwordHash) {
        UserEntity user = new UserEntity(username, email, passwordHash);
        userRepository.save(user);
        return mapToDTO(user);
    }
    public UserDTO createUser1(UserDTO userDTO, String rawPassword) {
        UserEntity user = new UserEntity();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(rawPassword);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return mapToDTO(user);
    }

    public Optional<UserDTO> findUserByUsername(String username) {
        return userRepository.findByUsername(username).map(this::mapToDTO);
    }

    public Optional<UserDTO> findUserByEmail(String email) {
        return userRepository.findByEmail(email).map(this::mapToDTO);
    }

    private UserDTO mapToDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setCreatedAt(userEntity.getCreatedAt());
        return userDTO;
    }
}
