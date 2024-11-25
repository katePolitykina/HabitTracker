package com.example.habitmanagerservise.repository;

import com.example.habitmanagerservise.entity.HabitEntity;
import com.example.habitmanagerservise.entity.HabitTrackingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitTrackingRepository extends JpaRepository<HabitTrackingEntity, Long> {
    boolean existsByHabitId(Long id);
}
