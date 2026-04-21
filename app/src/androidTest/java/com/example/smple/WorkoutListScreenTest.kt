package com.example.smple

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.smple.ui.workouts.WorkoutListScreen
import com.example.smple.ui.workouts.WorkoutViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WorkoutListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val app: Application
        get() = InstrumentationRegistry.getInstrumentation()
            .targetContext.applicationContext as Application

    @Test
    fun emptyStateMessage_isDisplayed_whenNoPlans() {
        val viewModel = WorkoutViewModel(app)
        composeTestRule.setContent {
            WorkoutListScreen(viewModel = viewModel, onPlanClick = {})
        }
        composeTestRule.onNodeWithText("No workout plans yet. Add one below.").assertIsDisplayed()
    }

    @Test
    fun newWorkoutRow_isDisplayed() {
        val viewModel = WorkoutViewModel(app)
        composeTestRule.setContent {
            WorkoutListScreen(viewModel = viewModel, onPlanClick = {})
        }
        composeTestRule.onNodeWithText("New Workout", substring = true).assertIsDisplayed()
    }

    @Test
    fun addPlanDialog_opensWhenNewWorkoutTapped() {
        val viewModel = WorkoutViewModel(app)
        composeTestRule.setContent {
            WorkoutListScreen(viewModel = viewModel, onPlanClick = {})
        }
        composeTestRule.onNodeWithText("New Workout", substring = true).performClick()
        composeTestRule.onNodeWithText("New Workout Plan").assertIsDisplayed()
    }
}
