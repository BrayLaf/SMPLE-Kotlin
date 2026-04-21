package com.example.smple

import com.example.smple.domain.model.Entry
import com.example.smple.domain.model.Plan
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDate

class ModelDefaultsTest {

    @Test
    fun entry_defaultValues_areCorrect() {
        val entry = Entry()
        assertEquals(0, entry.id)
        assertEquals("", entry.userId)
        assertNull(entry.planId)
        assertEquals("", entry.title)
        assertEquals("", entry.content)
        assertEquals("", entry.category)
        assertNull(entry.scheduledDate)
        assertEquals(0L, entry.createdAt)
    }

    @Test
    fun entry_copy_updatesOnlySpecifiedFields() {
        val original = Entry(id = 1, title = "Squat", content = "3x8")
        val updated = original.copy(content = "3x10")
        assertEquals(1, updated.id)
        assertEquals("Squat", updated.title)
        assertEquals("3x10", updated.content)
    }

    @Test
    fun plan_defaultValues_areCorrect() {
        val plan = Plan()
        assertEquals("", plan.id)
        assertEquals("", plan.userId)
        assertEquals("", plan.name)
        assertEquals(0L, plan.createdAt)
    }
}
