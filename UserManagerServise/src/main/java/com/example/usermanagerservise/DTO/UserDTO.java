package com.example.usermanagerservise.DTO;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;

    private LocalDateTime createdAt;

}