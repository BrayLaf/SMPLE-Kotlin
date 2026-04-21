package com.example.smple.data.remote.dto

import com.example.smple.domain.model.Entry
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.OffsetDateTime

@Serializable
data class EntryDto(
    val id: Int = 0,
    @SerialName("user_id") val userId: String = "",
    @SerialName("plan_id") val planId: String? = null,
    val title: String = "",
    val content: String = "",
    val category: String = "",
    @SerialName("parsed_json") val parsedJson: String? = null,
    @SerialName("scheduled_date") val scheduledDate: String? = null,
    @SerialName("created_at") val createdAt: String = "",
    @SerialName("updated_at") val updatedAt: String = "",
) {
    fun toDomain(): Entry = Entry(
        id = id,
        userId = userId,
        planId = planId,
        title = title,
        content = content,
        category = category,
        parsedJson = parsedJson,
        scheduledDate = scheduledDate?.let { runCatching { LocalDate.parse(it) }.getOrNull() },
        createdAt = runCatching { OffsetDateTime.parse(createdAt).toInstant().toEpochMilli() }.getOrDefault(0L),
        updatedAt = runCatching { OffsetDateTime.parse(updatedAt).toInstant().toEpochMilli() }.getOrDefault(0L),
    )
}

@Serializable
data class EntryInsertDto(
    @SerialName("user_id") val userId: String,
    @SerialName("plan_id") val planId: String? = null,
    val title: String,
    val content: String,
    val category: String,
    @SerialName("parsed_json") val parsedJson: String? = null,
    @SerialName("scheduled_date") val scheduledDate: String? = null,
    @SerialName("created_at") val createdAt: String,
)
