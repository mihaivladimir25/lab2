package com.example.demo.controller;

import com.example.demo.model.Workout;
import com.example.demo.service.WorkoutService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class WorkoutControllerTest {

    private WorkoutService workoutService;
    private WorkoutController controller;
    private ObjectMapper mapper;
    private Workout sampleWorkout;

    @BeforeEach
    void setUp() {
        workoutService = mock(WorkoutService.class);
        controller = new WorkoutController(workoutService);
        mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        sampleWorkout = new Workout(1L, "Run", 30, 300, LocalDate.of(2025, 5, 23));
    }

    @Test
    void testGetAll() {
        when(workoutService.getAllWorkouts()).thenReturn(List.of(sampleWorkout));

        ResponseEntity<List<Workout>> response = controller.getAll();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getType()).isEqualTo("Run");
    }

    @Test
    void testGetByIdFound() {
        when(workoutService.getWorkoutById(1L)).thenReturn(Optional.of(sampleWorkout));

        ResponseEntity<Workout> response = controller.getById(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getDuration()).isEqualTo(30);
    }

    @Test
    void testGetByIdNotFound() {
        when(workoutService.getWorkoutById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Workout> response = controller.getById(99L);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    void testAdd() {
        Workout input = new Workout(null, "Swim", 45, 400, LocalDate.of(2025, 5, 22));
        Workout saved = new Workout(2L, "Swim", 45, 400, LocalDate.of(2025, 5, 22));
        when(workoutService.saveWorkout(input)).thenReturn(saved);

        ResponseEntity<Workout> response = controller.add(input);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getId()).isEqualTo(2);
    }

    @Test
    void testDeleteFound() {
        when(workoutService.getWorkoutById(1L)).thenReturn(Optional.of(sampleWorkout));

        ResponseEntity<Void> response = controller.delete(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(workoutService).deleteWorkout(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(workoutService.getWorkoutById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = controller.delete(99L);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }
}
