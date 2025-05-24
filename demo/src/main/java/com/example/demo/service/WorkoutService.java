package com.example.demo.service;

import com.example.demo.model.Workout;
import com.example.demo.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {
    private final WorkoutRepository repository;

    public WorkoutService(WorkoutRepository repository) {
        this.repository = repository;
    }

    public List<Workout> getAllWorkouts() {
        return repository.findAll();
    }

    public Optional<Workout> getWorkoutById(Long id) {
        return repository.findById(id);
    }

    public Workout saveWorkout(Workout workout) {
        return repository.save(workout);
    }

    public void deleteWorkout(Long id) {
        repository.deleteById(id);
    }
}
