package com.example.smple

import com.example.smple.data.remote.dto.EntryDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class EntryDtoTest {

    @Test
    fun toDomain_validCreatedAt_parsesEpochMillis() {
        val dto = EntryDto(
            id = 1,
            userId = "user-1",
            createdAt = "2026-04-20T12:00:00+00:00",
            updatedAt = "2026-04-20T12:00:00+00:00",
        )
        assertTrue(dto.toDomain().createdAt > 0L)
    }

    @Test
    fun toDomain_invalidCreatedAt_fallsBackToZero() {
        val dto = EntryDto(createdAt = "not-a-timestamp", updatedAt = "")
        assertEquals(0L, dto.toDomain().createdAt)
    }

    @Test
    fun toDomain_nullPlanId_remainsNull() {
        val dto = EntryDto(planId = null, createdAt = "", updatedAt = "")
        assertNull(dto.toDomain().planId)
    }

    @Test
    fun toDomain_scheduledDatePresent_parsesCorrectly() {
        val dto = EntryDto(scheduledDate = "2026-04-22", createdAt = "", updatedAt = "")
        assertEquals(LocalDate.of(2026, 4, 22), dto.toDomain().scheduledDate)
    }

    @Test
    fun toDomain_nullScheduledDate_remainsNull() {
        val dto = EntryDto(scheduledDate = null, createdAt = "", updatedAt = "")
        assertNull(dto.toDomain().scheduledDate)
    }
}
