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
        val start = "${date}T00:00:00"
        val end = "${date.plusDays(1)}T00:00:00"
        return supabase.from("entries").select {
            filter {
                eq("user_id", userId)
                gte("created_at", start)
                lt("created_at", end)
            }
        }.decodeList<EntryDto>().map { it.toDomain() }
    }

    override suspend fun getEntriesForCategory(userId: String, category: String): List<Entry> {
        return supabase.from("entries").select {
            filter {
                eq("user_id", userId)
                eq("category", category)
            }
        }.decodeList<EntryDto>().map { it.toDomain() }
    }

    override suspend fun getTrainingDays(userId: String, month: YearMonth): Set<LocalDate> {
        val start = "${month.atDay(1)}T00:00:00"
        val end = "${month.atEndOfMonth().plusDays(1)}T00:00:00"
        return supabase.from("entries").select {
            filter {
                eq("user_id", userId)
                gte("created_at", start)
                lt("created_at", end)
            }
        }.decodeList<EntryDto>()
            .mapNotNull { dto ->
                runCatching {
                    LocalDate.parse(dto.createdAt.substringBefore("T"))
                }.getOrNull()
            }
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
            title = entry.title,
            content = entry.content,
            category = entry.category,
            parsedJson = entry.parsedJson,
            createdAt = createdAt,
        )
        supabase.from("entries").insert(dto)
    }

    override suspend fun deleteEntry(id: Int): Result<Unit> = runCatching {
        supabase.from("entries").delete {
            filter { eq("id", id) }
        }
    }
}
