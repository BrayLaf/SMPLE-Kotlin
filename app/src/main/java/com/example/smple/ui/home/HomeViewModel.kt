package com.example.smple.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.smple.SmpleApplication
import com.example.smple.domain.model.Entry
import com.example.smple.domain.model.Plan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneOffset

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val entryRepo = (application as SmpleApplication).entryRepository
    private val authRepo = (application as SmpleApplication).authRepository
    private val planRepo = (application as SmpleApplication).planRepository

    private val _currentMonth = MutableStateFlow(YearMonth.now())
    val currentMonth: StateFlow<YearMonth> = _currentMonth.asStateFlow()

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _entries = MutableStateFlow<List<Entry>>(emptyList())
    val entries: StateFlow<List<Entry>> = _entries.asStateFlow()

    private val _trainingDays = MutableStateFlow<Set<LocalDate>>(emptySet())
    val trainingDays: StateFlow<Set<LocalDate>> = _trainingDays.asStateFlow()

    private val _plans = MutableStateFlow<List<Plan>>(emptyList())
    val plans: StateFlow<List<Plan>> = _plans.asStateFlow()

    private val _dialogPlanEntries = MutableStateFlow<List<Entry>>(emptyList())
    val dialogPlanEntries: StateFlow<List<Entry>> = _dialogPlanEntries.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun clearError() { _error.value = null }

    init {
        loadEntriesForDate(LocalDate.now())
        loadTrainingDays(YearMonth.now())
        loadPlans()
    }

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        loadEntriesForDate(date)
    }

    private fun loadEntriesForDate(date: LocalDate) {
        viewModelScope.launch {
            val userId = authRepo.getCurrentUser()?.id ?: return@launch
            _entries.value = entryRepo.getEntriesForDate(userId, date)
        }
    }

    private fun loadTrainingDays(month: YearMonth) {
        viewModelScope.launch {
            val userId = authRepo.getCurrentUser()?.id ?: return@launch
            _trainingDays.value = entryRepo.getTrainingDays(userId, month)
        }
    }

    private fun loadPlans() {
        viewModelScope.launch {
            val userId = authRepo.getCurrentUser()?.id ?: return@launch
            _plans.value = planRepo.getPlans(userId)
        }
    }

    fun loadDialogPlanEntries(planId: String) {
        viewModelScope.launch {
            _dialogPlanEntries.value = entryRepo.getEntriesForPlan(planId)
        }
    }

    fun previousMonth() {
        _currentMonth.update { it.minusMonths(1) }
        loadTrainingDays(_currentMonth.value)
    }

    fun nextMonth() {
        _currentMonth.update { it.plusMonths(1) }
        loadTrainingDays(_currentMonth.value)
    }

    fun logWorkout(date: LocalDate, planId: String, entryTitle: String, notes: String) {
        viewModelScope.launch {
            val userId = authRepo.getCurrentUser()?.id
            if (userId == null) {
                _error.value = "Not signed in. Please sign in and try again."
                return@launch
            }
            val epochMillis = date.atTime(12, 0).toInstant(ZoneOffset.UTC).toEpochMilli()
            val entry = Entry(
                userId = userId,
                planId = null,
                title = entryTitle,
                category = "",
                content = notes,
                scheduledDate = date,
                createdAt = epochMillis,
            )
            entryRepo.createEntry(entry)
                .onSuccess {
                    loadEntriesForDate(_selectedDate.value)
                    loadTrainingDays(_currentMonth.value)
                }
                .onFailure { _error.value = "Failed to save workout: ${it.message}" }
        }
    }

    fun deleteEntry(id: Int) {
        viewModelScope.launch {
            entryRepo.deleteEntry(id)
            loadEntriesForDate(_selectedDate.value)
            loadTrainingDays(_currentMonth.value)
        }
    }
}
