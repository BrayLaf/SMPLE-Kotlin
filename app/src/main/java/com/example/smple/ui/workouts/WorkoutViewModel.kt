package com.example.smple.ui.workouts

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WorkoutViewModel : ViewModel() {

    // TODO: inject EntryRepository when DI is set up

    private val _workoutPlans = MutableStateFlow(listOf("Workout 1", "Workout 2"))
    val workoutPlans: StateFlow<List<String>> = _workoutPlans.asStateFlow()

    val categories: StateFlow<List<String>> = MutableStateFlow(listOf("Push", "Pull", "Legs"))

    // Placeholder exercises keyed by category — replace with repo data later
    private val sampleExercises = mapOf(
        "Push" to listOf(
            "Bench 3x12 (225)",
            "Dips 4x10 (45)",
            "Push Ups 10x10 (body weight)",
            "Bench 3x12 (225)",
        ),
        "Pull" to listOf(
            "Pull Ups 4x8 (body weight)",
            "Barbell Row 3x12 (135)",
            "Curl 3x10 (35)",
        ),
        "Legs" to listOf(
            "Squat 4x8 (315)",
            "Leg Press 3x12 (360)",
            "Calf Raises 4x15 (body weight)",
        ),
    )

    private val _exercises = MutableStateFlow<List<String>>(emptyList())
    val exercises: StateFlow<List<String>> = _exercises.asStateFlow()

    fun loadExercises(category: String) {
        _exercises.value = sampleExercises[category] ?: emptyList()
    }

    fun addPlan(name: String) {
        if (name.isNotBlank()) _workoutPlans.value = _workoutPlans.value + name
    }
}
