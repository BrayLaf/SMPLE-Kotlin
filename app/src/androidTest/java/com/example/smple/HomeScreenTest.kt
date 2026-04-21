package com.example.smple

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.smple.ui.home.HomeScreen
import com.example.smple.ui.home.HomeViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val app: Application
        get() = InstrumentationRegistry.getInstrumentation()
            .targetContext.applicationContext as Application

    @Test
    fun logButton_isDisplayed() {
        val viewModel = HomeViewModel(app)
        composeTestRule.setContent {
            HomeScreen(viewModel = viewModel, onEntryClick = {})
        }
        composeTestRule.onNodeWithText("+ Log").assertIsDisplayed()
    }

    @Test
    fun todaysGainsLabel_isDisplayed() {
        val viewModel = HomeViewModel(app)
        composeTestRule.setContent {
            HomeScreen(viewModel = viewModel, onEntryClick = {})
        }
        composeTestRule.onNodeWithText("Today's Gains").assertIsDisplayed()
    }

    @Test
    fun emptyWorkoutsMessage_isDisplayed_whenNoEntries() {
        val viewModel = HomeViewModel(app)
        composeTestRule.setContent {
            HomeScreen(viewModel = viewModel, onEntryClick = {})
        }
        composeTestRule.onNodeWithText("No workouts logged.").assertIsDisplayed()
    }
}
