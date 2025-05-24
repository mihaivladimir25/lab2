package com.example.demo.controller;

import com.example.demo.model.Workout;
import com.example.demo.service.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutService service;

    public WorkoutController(WorkoutService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Workout>> getAll() {
        return ResponseEntity.ok(service.getAllWorkouts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workout> getById(@PathVariable Long id) {
        return service.getWorkoutById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Workout> add(@RequestBody Workout workout) {
        return ResponseEntity.ok(service.saveWorkout(workout));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.getWorkoutById(id)
                .map(w -> {
                    service.deleteWorkout(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
