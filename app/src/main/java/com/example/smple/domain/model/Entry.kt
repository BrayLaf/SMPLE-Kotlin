package com.example.smple.domain.model

data class Entry(
    val id: Int = 0,
    val userId: String = "",
    val title: String = "",
    val content: String = "",
    val category: String = "",
    val parsedJson: String? = null,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
)
