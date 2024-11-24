package com.example.habitmanagerservise.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HabitController {

    @GetMapping("/habits")
    public String getHabits() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        // Используйте `username` для определения пользователя и работы с его привычками.
        return "Habits for user: " + username;
    }
}
