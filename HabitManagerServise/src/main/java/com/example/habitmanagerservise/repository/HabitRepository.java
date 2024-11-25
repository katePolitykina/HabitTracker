package com.example.habitmanagerservise.repository;

import com.example.habitmanagerservise.entity.HabitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HabitRepository extends JpaRepository<HabitEntity, Long> {
    Boolean existsByUserEmailAndName(String user_email, String name);

    Optional<HabitEntity> findByIdAndUserEmail(Long habitId, String user);

    HabitEntity findByNameAndUserEmail(String testHabit, String userEmail);
}