package com.example.smple.ui.home

import androidx.lifecycle.ViewModel
import com.example.smple.domain.model.Entry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.YearMonth

class HomeViewModel : ViewModel() {

    // TODO: inject EntryRepository when DI is set up

    private val _currentMonth = MutableStateFlow(YearMonth.now())
    val currentMonth: StateFlow<YearMonth> = _currentMonth.asStateFlow()

    private val _recentEntries = MutableStateFlow<List<Entry>>(emptyList())
    val recentEntries: StateFlow<List<Entry>> = _recentEntries.asStateFlow()

    private val _trainingDays = MutableStateFlow<Set<LocalDate>>(emptySet())
    val trainingDays: StateFlow<Set<LocalDate>> = _trainingDays.asStateFlow()

    fun previousMonth() = _currentMonth.update { it.minusMonths(1) }
    fun nextMonth() = _currentMonth.update { it.plusMonths(1) }
}
