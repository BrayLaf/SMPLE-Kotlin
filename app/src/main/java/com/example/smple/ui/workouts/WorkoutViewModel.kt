package com.example.smple.ui.workouts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.smple.SmpleApplication
import com.example.smple.domain.model.Entry
import com.example.smple.domain.model.Plan
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

    init {
        loadPlans()
    }

    fun loadPlans() {
        viewModelScope.launch {
            val userId = authRepo.getCurrentUser()?.id ?: return@launch
            _plans.value = planRepo.getPlans(userId)
        }
    }

    fun addPlan(name: String) {
        val trimmed = name.trim()
        if (trimmed.isBlank()) return

        viewModelScope.launch {
            val userId = authRepo.getCurrentUser()?.id ?: return@launch
            planRepo.createPlan(userId, trimmed)
                .onSuccess { loadPlans() }
        }
    }

    fun loadWorkouts(planId: String) {
        viewModelScope.launch {
            val userId = authRepo.getCurrentUser()?.id ?: return@launch
            _workouts.value = entryRepo.getEntriesForCategory(userId, planId)
        }
    }

    fun loadWorkout(entryId: Int) {
        viewModelScope.launch {
            _selectedWorkout.value = entryRepo.getEntryById(entryId)
        }
    }
}
