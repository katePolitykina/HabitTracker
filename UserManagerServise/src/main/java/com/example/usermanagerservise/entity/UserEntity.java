package com.example.usermanagerservise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
    @Table(name = "users")
    @Getter
    @Setter
    @AllArgsConstructor
    public class UserEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true, length = 100)
        private String username;

        @Column(nullable = false, unique = true, length = 225)
        private String email;

        @Column(nullable = false, length = 225)
        private String passwordHash;

        @Column(nullable = false)
        private LocalDateTime createdAt;

        @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
        private List<Role> roles = new ArrayList<>();

        public UserEntity() {
            this.createdAt = LocalDateTime.now();
        }
        public UserEntity(String username, String email, String passwordHash) {
            this.username = username;
            this.email = email;
            this.passwordHash = passwordHash;
            this.createdAt = LocalDateTime.now();
        }


    }


