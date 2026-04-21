package com.example.smple

import com.example.smple.navigation.Screen
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NavigationRouteTest {

    @Test
    fun workoutPlanDetail_createRoute_embedsPlanId() {
        val planId = "afa2b7e8-03ad-479e-86bf-3d73f469b95d"
        val route = Screen.WorkoutPlanDetail.createRoute(planId)
        assertTrue(route.contains(planId))
        assertEquals("workout_plan/$planId", route)
    }

    @Test
    fun workoutDetail_createRoute_embedsEntryId() {
        val entryId = 42
        val route = Screen.WorkoutDetail.createRoute(entryId)
        assertTrue(route.contains(entryId.toString()))
        assertEquals("workout_detail/$entryId", route)
    }
}
