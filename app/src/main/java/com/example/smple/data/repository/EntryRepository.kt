package com.example.smple.data.repository

import com.example.smple.domain.model.Entry
import java.time.LocalDate
import java.time.YearMonth

interface EntryRepository {
    suspend fun getEntriesForDate(userId: String, date: LocalDate): List<Entry>
    suspend fun getTrainingDays(userId: String, month: YearMonth): Set<LocalDate>
    suspend fun createEntry(entry: Entry): Result<Unit>
    suspend fun deleteEntry(id: Int): Result<Unit>
}
