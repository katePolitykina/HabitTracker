package com.example.habitmanagerservise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "habit_tracking")
@Getter
@Setter
@AllArgsConstructor
public class HabitTrackingEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", referencedColumnName = "id") // внешний ключ в таблице Habit
    private HabitEntity habit;

    private LocalDateTime dateCompleted;

    public HabitTrackingEntity() {
        this.dateCompleted = LocalDateTime.now();
    }

}
