package com.example.smple.ui.workouts

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.smple.SmpleApplication
import com.example.smple.domain.model.Entry
import com.example.smple.domain.model.Plan
import java.time.Instant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val entryRepo = (application as SmpleApplication).entryRepository
    private val authRepo = (application as SmpleApplication).authRepository
    private val planRepo = (application as SmpleApplication).planRepository

    private val _plans = MutableStateFlow<List<Plan>>(emptyList())
    val plans: StateFlow<List<Plan>> = _plans.asStateFlow()

    private val _workouts = MutableStateFlow<List<Entry>>(emptyList())
    val workouts: StateFlow<List<Entry>> = _workouts.asStateFlow()

    private val _selectedWorkout = MutableStateFlow<Entry?>(null)
    val selectedWorkout: StateFlow<Entry?> = _selectedWorkout.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun clearError() { _error.value = null }

    init {
        loadPlans()
    }

    fun loadPlans() {
        viewModelScope.launch {
            val userId = authRepo.getCurrentUser()?.id ?: return@launch
            _plans.value = planRepo.getPlans(userId)
        }
    }

    fun deletePlan(id: String) {
        viewModelScope.launch {
            planRepo.deletePlan(id)
                .onSuccess { loadPlans() }
                .onFailure { _error.value = "Failed to delete plan: ${it.message}" }
        }
    }

    fun addPlan(name: String) {
        val trimmed = name.trim()
        if (trimmed.isBlank()) return

        viewModelScope.launch {
            val userId = authRepo.getCurrentUser()?.id
            Log.d("WorkoutVM", "addPlan: userId=$userId name=$trimmed")
            if (userId == null) {
                Log.e("WorkoutVM", "addPlan: no user logged in")
                _error.value = "Not signed in"
                return@launch
            }
            planRepo.createPlan(userId, trimmed)
                .onSuccess {
                    Log.d("WorkoutVM", "addPlan: success")
                    loadPlans()
                }
                .onFailure {
                    Log.e("WorkoutVM", "addPlan: failed", it)
                    _error.value = "Failed to create plan: ${it.message}"
                }
        }
    }

    private var currentPlanId: String = ""

    fun loadWorkouts(planId: String) {
        currentPlanId = planId
        viewModelScope.launch {
            _workouts.value = entryRepo.getEntriesForPlan(planId)
        }
    }

    fun addWorkoutToPlan(planId: String, name: String, notes: String) {
        val trimmed = name.trim()
        if (trimmed.isBlank()) return
        viewModelScope.launch {
            val userId = authRepo.getCurrentUser()?.id
            Log.d("WorkoutVM", "addWorkoutToPlan: userId=$userId planId=$planId name=$trimmed")
            if (userId == null) {
                Log.e("WorkoutVM", "addWorkoutToPlan: no user logged in")
                _error.value = "Not signed in"
                return@launch
            }
            val entry = Entry(
                userId = userId,
                planId = planId,
                title = trimmed,
                content = notes,
                category = "",
                createdAt = Instant.now().toEpochMilli(),
            )
            entryRepo.createEntry(entry)
                .onSuccess {
                    Log.d("WorkoutVM", "addWorkoutToPlan: success")
                    loadWorkouts(planId)
                }
                .onFailure {
                    Log.e("WorkoutVM", "addWorkoutToPlan: failed", it)
                    _error.value = "Failed to add workout: ${it.message}"
                }
        }
    }

    fun loadWorkout(entryId: Int) {
        viewModelScope.launch {
            _selectedWorkout.value = entryRepo.getEntryById(entryId)
        }
    }

    fun updateWorkout(id: Int, title: String, content: String) {
        viewModelScope.launch {
            entryRepo.updateEntry(id, title, content)
                .onSuccess { _selectedWorkout.value = _selectedWorkout.value?.copy(title = title, content = content) }
                .onFailure { _error.value = "Failed to update: ${it.message}" }
        }
    }

    fun deleteWorkout(id: Int, planId: String) {
        viewModelScope.launch {
            entryRepo.deleteEntry(id)
                .onSuccess { loadWorkouts(planId) }
                .onFailure { _error.value = "Failed to delete: ${it.message}" }
        }
    }
}
