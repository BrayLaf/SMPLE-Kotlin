package com.example.smple.data.repository

import com.example.smple.domain.model.Plan

interface PlanRepository {
    suspend fun getPlans(userId: String): List<Plan>
    suspend fun createPlan(userId: String, name: String): Result<Unit>
    suspend fun deletePlan(id: String): Result<Unit>
}

