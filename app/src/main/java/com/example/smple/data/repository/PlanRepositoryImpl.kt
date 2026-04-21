package com.example.smple.data.repository

import com.example.smple.data.remote.dto.PlanDto
import com.example.smple.data.remote.dto.PlanInsertDto
import com.example.smple.domain.model.Plan
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class PlanRepositoryImpl(private val supabase: SupabaseClient) : PlanRepository {

    override suspend fun getPlans(userId: String): List<Plan> {
        return supabase.from("plans").select {
            filter { eq("user_id", userId) }
        }.decodeList<PlanDto>()
            .map { it.toDomain() }
            .sortedByDescending { it.createdAt }
    }

    override suspend fun createPlan(userId: String, name: String): Result<Unit> = runCatching {
        val dto = PlanInsertDto(
            userId = userId,
            name = name,
        )
        supabase.from("plans").insert(dto)
    }

    override suspend fun deletePlan(id: String): Result<Unit> = runCatching {
        supabase.from("plans").delete {
            filter { eq("id", id) }
        }
    }
}


