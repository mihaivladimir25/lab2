package com.example.demo.service;

import com.example.demo.model.Workout;
import com.example.demo.repository.WorkoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkoutServiceTest {

    private WorkoutRepository repo;
    private WorkoutService service;

    @BeforeEach
    void setUp() {
        repo = mock(WorkoutRepository.class);
        service = new WorkoutService(repo);
    }

    @Test
    void testGetAllWorkouts() {
        List<Workout> mockList = Arrays.asList(
                new Workout(null, "Running", 30, 300, LocalDate.now())
        );
        when(repo.findAll()).thenReturn(mockList);

        List<Workout> result = service.getAllWorkouts();
        assertEquals(1, result.size());
        assertEquals("Running", result.get(0).getType());
    }

    @Test
    void testGetWorkoutByIdFound() {
        Workout workout = new Workout(1L, "Cycling", 45, 400, LocalDate.now());
        when(repo.findById(1L)).thenReturn(Optional.of(workout));

        Optional<Workout> result = service.getWorkoutById(1L);
        assertTrue(result.isPresent());
        assertEquals("Cycling", result.get().getType());
    }

    @Test
    void testGetWorkoutByIdNotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        Optional<Workout> result = service.getWorkoutById(99L);
        assertFalse(result.isPresent());
    }

    @Test
    void testSaveWorkout() {
        Workout input = new Workout(null, "Swim", 60, 600, LocalDate.now());
        Workout saved = new Workout(1L, "Swim", 60, 600, LocalDate.now());
        when(repo.save(input)).thenReturn(saved);

        Workout result = service.saveWorkout(input);
        assertNotNull(result.getId());
        assertEquals("Swim", result.getType());
    }

    @Test
    void testDeleteWorkout() {
        service.deleteWorkout(1L);
        verify(repo, times(1)).deleteById(1L);
    }
}
