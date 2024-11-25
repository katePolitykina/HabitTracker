package com.example.habitmanagerservise.controller;

import com.example.habitmanagerservise.DTO.HabitCreationRequestDTO;
import com.example.habitmanagerservise.DTO.HabitDTO;
import com.example.habitmanagerservise.entity.HabitEntity;
import com.example.habitmanagerservise.servise.HabitService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HabitController {

    HabitService habitService;

    @PostMapping("/habits/add")
    public ResponseEntity<String> addHabit(@RequestBody HabitCreationRequestDTO habitCreationRequestDTO) {
        return habitService.createHabit(habitCreationRequestDTO);
    }
    @PostMapping("/habits/delete")
    public ResponseEntity<String> deleteHabit(@RequestBody long id) {
        return habitService.deleteHabit(id);
    }
    @PostMapping("/habits/complete")
    public ResponseEntity<String> markHabitAsCompleted(@RequestBody long id) {
        return habitService.markHabitAsCompleted(id);
    }

}
