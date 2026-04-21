package com.example.smple.data.repository

import com.example.smple.domain.model.Entry
import java.time.LocalDate
import java.time.YearMonth

interface EntryRepository {
    suspend fun getEntriesForDate(userId: String, date: LocalDate): List<Entry>
    suspend fun getEntriesForPlan(planId: String): List<Entry>
    suspend fun getTrainingDays(userId: String, month: YearMonth): Set<LocalDate>
    suspend fun getEntryById(id: Int): Entry?
    suspend fun createEntry(entry: Entry): Result<Unit>
    suspend fun updateEntry(id: Int, title: String, content: String): Result<Unit>
    suspend fun deleteEntry(id: Int): Result<Unit>
}
