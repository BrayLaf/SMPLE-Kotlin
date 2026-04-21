package com.example.smple.data.repository

import com.example.smple.data.remote.dto.EntryDto
import com.example.smple.data.remote.dto.EntryInsertDto
import com.example.smple.domain.model.Entry
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneOffset

class EntryRepositoryImpl(private val supabase: SupabaseClient) : EntryRepository {

    override suspend fun getEntriesForDate(userId: String, date: LocalDate): List<Entry> {
        return supabase.from("entries").select {
            filter {
                eq("user_id", userId)
                eq("scheduled_date", date.toString())
            }
        }.decodeList<EntryDto>()
            .filter { it.planId == null }
            .map { it.toDomain() }
    }

    override suspend fun getEntriesForPlan(planId: String): List<Entry> {
        return supabase.from("entries").select {
            filter { eq("plan_id", planId) }
        }.decodeList<EntryDto>().map { it.toDomain() }
    }

    override suspend fun getTrainingDays(userId: String, month: YearMonth): Set<LocalDate> {
        return supabase.from("entries").select {
            filter {
                eq("user_id", userId)
                gte("scheduled_date", month.atDay(1).toString())
                lte("scheduled_date", month.atEndOfMonth().toString())
            }
        }.decodeList<EntryDto>()
            .filter { it.planId == null && it.scheduledDate != null }
            .mapNotNull { runCatching { LocalDate.parse(it.scheduledDate!!) }.getOrNull() }
            .toSet()
    }

    override suspend fun getEntryById(id: Int): Entry? {
        return supabase.from("entries").select {
            filter { eq("id", id) }
        }.decodeList<EntryDto>().firstOrNull()?.toDomain()
    }

    override suspend fun createEntry(entry: Entry): Result<Unit> = runCatching {
        val createdAt = Instant.ofEpochMilli(entry.createdAt)
            .atOffset(ZoneOffset.UTC)
            .toString()
        val dto = EntryInsertDto(
            userId = entry.userId,
            planId = entry.planId,
            title = entry.title,
            content = entry.content,
            category = entry.category,
            parsedJson = entry.parsedJson,
            scheduledDate = entry.scheduledDate?.toString(),
            createdAt = createdAt,
        )
        supabase.from("entries").insert(dto)
    }

    override suspend fun updateEntry(id: Int, title: String, content: String): Result<Unit> = runCatching {
        supabase.from("entries").update({
            set("title", title)
            set("content", content)
        }) {
            filter { eq("id", id) }
        }
    }

    override suspend fun deleteEntry(id: Int): Result<Unit> = runCatching {
        supabase.from("entries").delete {
            filter { eq("id", id) }
        }
    }
}
