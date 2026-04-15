package com.example.smple.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.smple.data.local.dao.EntryDao
import com.example.smple.data.local.dao.UserDao
import com.example.smple.data.local.entity.EntryEntity
import com.example.smple.data.local.entity.UserEntity

@Database(
    entities = [EntryEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class SmpleDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao
    abstract fun userDao(): UserDao
}
