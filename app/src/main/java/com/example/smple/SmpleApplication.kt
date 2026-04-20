package com.example.smple

import android.app.Application
import androidx.room.Room
import com.example.smple.data.local.SmpleDatabase
import com.example.smple.data.remote.SupabaseModule
import com.example.smple.data.repository.AuthRepository
import com.example.smple.data.repository.AuthRepositoryImpl
import com.example.smple.data.repository.EntryRepository
import com.example.smple.data.repository.EntryRepositoryImpl
import com.example.smple.data.repository.PlanRepository
import com.example.smple.data.repository.PlanRepositoryImpl

class SmpleApplication : Application() {

    val supabase by lazy { SupabaseModule.client }

    val database by lazy {
        Room.databaseBuilder(this, SmpleDatabase::class.java, "smple.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    val authRepository: AuthRepository by lazy { AuthRepositoryImpl(supabase) }
    val entryRepository: EntryRepository by lazy { EntryRepositoryImpl(supabase) }
    val planRepository: PlanRepository by lazy { PlanRepositoryImpl(supabase) }
}
