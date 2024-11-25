package com.example.habitmanagerservise.servise;

import com.example.habitmanagerservise.DTO.HabitCreationRequestDTO;
import com.example.habitmanagerservise.entity.HabitEntity;
import com.example.habitmanagerservise.entity.HabitTrackingEntity;
import com.example.habitmanagerservise.repository.HabitRepository;
import com.example.habitmanagerservise.repository.HabitTrackingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class HabitServiceTest {
    @Mock
    private HabitRepository habitRepository;

    @Mock
    private HabitTrackingRepository habitTrackingRepository;

    @InjectMocks
    private HabitService habitService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    HabitServiceTest() {
    }

    @Test
    public void createHabit_ShouldReturnSuccess_WhenHabitIsNew() {
        // Arrange
        HabitCreationRequestDTO requestDTO = new HabitCreationRequestDTO("Test Habit", "Test Description");
        String userEmail = "test@example.com";

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userEmail);
        when(habitRepository.existsByUserEmailAndName(userEmail, requestDTO.getName())).thenReturn(false);

        // Act
        ResponseEntity<String> response = habitService.createHabit(requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Habit adding success", response.getBody());
        verify(habitRepository, times(1)).save(any(HabitEntity.class));
    }

    @Test
    public void createHabit_ShouldReturnError_WhenHabitExists() {
        // Arrange
        HabitCreationRequestDTO requestDTO = new HabitCreationRequestDTO("Test Habit", "Test Description");
        String userEmail = "test@example.com";

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userEmail);
        when(habitRepository.existsByUserEmailAndName(userEmail, requestDTO.getName())).thenReturn(true);

        // Act
        ResponseEntity<String> response = habitService.createHabit(requestDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Habit already exists", response.getBody());
    }

    @Test
    public void deleteHabit_ShouldReturnSuccess_WhenHabitExists() {
        // Arrange
        Long habitId = 1L;
        String userEmail = "test@example.com";
        HabitEntity habit = new HabitEntity();
        habit.setId(habitId);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userEmail);
        when(habitRepository.findByIdAndUserEmail(habitId, userEmail)).thenReturn(Optional.of(habit));

        // Act
        ResponseEntity<String> response = habitService.deleteHabit(habitId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Habit deleted successfully", response.getBody());
        verify(habitRepository, times(1)).deleteById(habitId);
    }

    @Test
    public void deleteHabit_ShouldReturnError_WhenHabitNotFound() {
        // Arrange
        Long habitId = 1L;
        String userEmail = "test@example.com";

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userEmail);
        when(habitRepository.findByIdAndUserEmail(habitId, userEmail)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = habitService.deleteHabit(habitId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Habit not found", response.getBody());
    }

    @Test
    public void markHabitAsCompleted_ShouldReturnSuccess_WhenHabitExists() {
        // Arrange
        Long habitId = 1L;
        String userEmail = "test@example.com";
        HabitEntity habit = new HabitEntity();
        habit.setId(habitId);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userEmail);
        when(habitRepository.findByIdAndUserEmail(habitId, userEmail)).thenReturn(Optional.of(habit));

        // Act
        ResponseEntity<String> response = habitService.markHabitAsCompleted(habitId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Habit marked as completed", response.getBody());
        verify(habitTrackingRepository, times(1)).save(any(HabitTrackingEntity.class));
    }

    @Test
    public void markHabitAsCompleted_ShouldReturnError_WhenHabitNotFound() {
        // Arrange
        Long habitId = 1L;
        String userEmail = "test@example.com";

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userEmail);
        when(habitRepository.findByIdAndUserEmail(habitId, userEmail)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = habitService.markHabitAsCompleted(habitId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Habit not found or you are not authorized", response.getBody());
    }
}