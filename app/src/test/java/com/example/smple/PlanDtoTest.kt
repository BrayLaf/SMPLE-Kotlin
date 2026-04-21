package com.example.smple

import com.example.smple.data.remote.dto.PlanDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PlanDtoTest {

    @Test
    fun toDomain_mapsAllFieldsCorrectly() {
        val dto = PlanDto(
            id = "plan-123",
            userId = "user-456",
            name = "Push Day",
            createdAt = "2026-04-20T10:00:00+00:00",
        )
        val plan = dto.toDomain()
        assertEquals("plan-123", plan.id)
        assertEquals("user-456", plan.userId)
        assertEquals("Push Day", plan.name)
        assertTrue(plan.createdAt > 0L)
    }

    @Test
    fun toDomain_invalidTimestamp_fallsBackToZero() {
        val dto = PlanDto(id = "1", createdAt = "bad-date")
        assertEquals(0L, dto.toDomain().createdAt)
    }

    @Test
    fun toDomain_emptyCreatedAt_fallsBackToZero() {
        val dto = PlanDto(createdAt = "")
        assertEquals(0L, dto.toDomain().createdAt)
    }
}
