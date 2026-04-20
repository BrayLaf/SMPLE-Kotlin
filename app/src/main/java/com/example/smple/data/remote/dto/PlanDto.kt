package com.example.smple.data.remote.dto

import com.example.smple.domain.model.Plan
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class PlanDto(
    val id: String = "",
    @SerialName("user_id") val userId: String = "",
    val name: String = "",
    @SerialName("created_at") val createdAt: String = "",
) {
    fun toDomain(): Plan = Plan(
        id = id,
        userId = userId,
        name = name,
        createdAt = runCatching { OffsetDateTime.parse(createdAt).toInstant().toEpochMilli() }.getOrDefault(0L),
    )
}

@Serializable
data class PlanInsertDto(
    @SerialName("user_id") val userId: String,
    val name: String,
)

