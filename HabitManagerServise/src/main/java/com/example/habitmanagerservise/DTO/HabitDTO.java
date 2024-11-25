package com.example.habitmanagerservise.DTO;

import com.example.habitmanagerservise.enums.FrequencyEnum;

import java.time.LocalDateTime;

public class HabitDTO {
    private String description;
    private LocalDateTime createdAt;
    private String name;
    private LocalDateTime startDate;
    private FrequencyEnum frequency;
    private Long user_id;
}
