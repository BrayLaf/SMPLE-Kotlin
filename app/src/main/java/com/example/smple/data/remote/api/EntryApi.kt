package com.example.smple.data.remote.api

import com.example.smple.data.remote.dto.EntryDto
import com.example.smple.data.remote.dto.EntryInsertDto

interface EntryApi {
    suspend fun getEntriesForDate(userId: String, start: String, end: String): List<EntryDto>
    suspend fun getEntriesForMonth(userId: String, start: String, end: String): List<EntryDto>
    suspend fun createEntry(entry: EntryInsertDto)
    suspend fun deleteEntry(id: Int)
}
