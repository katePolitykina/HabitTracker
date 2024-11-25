package com.example.habitmanagerservise.DTO;

import com.example.habitmanagerservise.enums.FrequencyEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HabitCreationRequestDTO {

    private String description;
    private String name;
 //   private LocalDateTime startDate;
 //   private FrequencyEnum frequency = FrequencyEnum.DAILY;

}
