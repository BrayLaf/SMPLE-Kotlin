package com.example.smple

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.smple.ui.auth.OnboardingScreen
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnboardingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginButton_isDisplayed() {
        composeTestRule.setContent {
            OnboardingScreen(onSignUpClick = {}, onLoginClick = {})
        }
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
    }

    @Test
    fun signUpButton_isDisplayed() {
        composeTestRule.setContent {
            OnboardingScreen(onSignUpClick = {}, onLoginClick = {})
        }
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
    }

    @Test
    fun loginButton_click_triggersCallback() {
        var clicked = false
        composeTestRule.setContent {
            OnboardingScreen(onSignUpClick = {}, onLoginClick = { clicked = true })
        }
        composeTestRule.onNodeWithText("Login").performClick()
        assertTrue(clicked)
    }

    @Test
    fun signUpButton_click_triggersCallback() {
        var clicked = false
        composeTestRule.setContent {
            OnboardingScreen(onSignUpClick = { clicked = true }, onLoginClick = {})
        }
        composeTestRule.onNodeWithText("Sign Up").performClick()
        assertTrue(clicked)
    }
}
