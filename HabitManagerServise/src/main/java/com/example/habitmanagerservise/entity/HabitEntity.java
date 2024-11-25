package com.example.habitmanagerservise.entity;

import com.example.habitmanagerservise.enums.FrequencyEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;



@Entity
@Table(name = "habit")
@Getter
@Setter
@AllArgsConstructor
public class HabitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 225)
    private String description;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false, length = 225)
    private String name;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime startDate = LocalDateTime.now();
    @Column(nullable = false)
    @Builder.Default
    private FrequencyEnum frequency = FrequencyEnum.DAILY;
    @Column(nullable = false, length = 225)
    private String userEmail;
    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HabitTrackingEntity> habitTrackingEntities;

    public HabitEntity() {
        this.createdAt = LocalDateTime.now();
    }
}









