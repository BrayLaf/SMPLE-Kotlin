package com.example.smple.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EntryDto(
    val id: Int = 0,
    @SerialName("user_id") val userId: Int = 0,
    val title: String = "",
    val content: String = "",
    val category: String = "",
    @SerialName("parsed_json") val parsedJson: String? = null,
    @SerialName("created_at") val createdAt: String = "",
    @SerialName("updated_at") val updatedAt: String = "",
)
