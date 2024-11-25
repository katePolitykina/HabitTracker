package com.example.habitmanagerservise.servise;

import com.example.habitmanagerservise.DTO.HabitCreationRequestDTO;
import com.example.habitmanagerservise.entity.HabitEntity;
import com.example.habitmanagerservise.entity.HabitTrackingEntity;
import com.example.habitmanagerservise.repository.HabitRepository;
import com.example.habitmanagerservise.repository.HabitTrackingRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HabitService {

    private final HabitRepository habitRepository;
    private final HabitTrackingRepository habitTrackingRepository;



    public ResponseEntity<String> createHabit(@RequestBody HabitCreationRequestDTO requestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = (String) authentication.getPrincipal();

        if (habitRepository.existsByUserEmailAndName(user, requestDTO.getName())) {
            return new ResponseEntity<>("Habit already exists", HttpStatus.BAD_REQUEST);
        }

        HabitEntity habit = new HabitEntity();
        habit.setName(requestDTO.getName());
        habit.setDescription(requestDTO.getDescription());
     //   habit.setFrequency(requestDTO.getFrequency());
        habit.setStartDate(LocalDateTime.now());
        habit.setUserEmail(user);
        habitRepository.save(habit);
        return new ResponseEntity<>("Habit adding success", HttpStatus.OK );
    }
    public ResponseEntity<String> deleteHabit(Long habitId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = (String) authentication.getPrincipal();

        Optional<HabitEntity> habitOptional = habitRepository.findByIdAndUserEmail(habitId, user);

        if (habitOptional.isEmpty()) {
            return new ResponseEntity<>("Habit not found", HttpStatus.NOT_FOUND);
        }

        habitRepository.deleteById(habitId);
        return new ResponseEntity<>("Habit deleted successfully", HttpStatus.OK);
    }
    public ResponseEntity<String> markHabitAsCompleted(Long habitId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = (String) authentication.getPrincipal();

        // Найти привычку по ID и email пользователя
        Optional<HabitEntity> optionalHabit = habitRepository.findByIdAndUserEmail(habitId, userEmail);

        if (optionalHabit.isEmpty()) {
            return new ResponseEntity<>("Habit not found or you are not authorized", HttpStatus.NOT_FOUND);
        }

        HabitEntity habit = optionalHabit.get();

        // Создать новую запись в HabitTrackingEntity
        HabitTrackingEntity habitTracking = new HabitTrackingEntity();
        habitTracking.setHabit(habit);
        habitTracking.setDateCompleted(LocalDateTime.now());

        habitTrackingRepository.save(habitTracking);

        return new ResponseEntity<>("Habit marked as completed", HttpStatus.OK);
    }


}
