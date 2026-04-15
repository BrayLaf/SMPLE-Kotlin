package com.example.smple.domain.model

data class Entry(
    val id: Int = 0,
    val userId: Int = 0,
    val title: String = "",
    val content: String = "",
    val category: String = "",   // e.g. "Push", "Pull", "Legs"
    val parsedJson: String? = null,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
)
