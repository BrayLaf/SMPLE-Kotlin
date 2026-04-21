package com.example.smple.domain.model

import java.time.LocalDate

data class Entry(
    val id: Int = 0,
    val userId: String = "",
    val planId: String? = null,
    val title: String = "",
    val content: String = "",
    val category: String = "",
    val parsedJson: String? = null,
    val scheduledDate: LocalDate? = null,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
)
