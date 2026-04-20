package com.example.smple.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class EntryEntity(
    @PrimaryKey val id: Int = 0,
    val userId: String = "",
    val title: String = "",
    val content: String = "",
    val category: String = "",
    val parsedJson: String? = null,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    val isSynced: Boolean = false,
)
