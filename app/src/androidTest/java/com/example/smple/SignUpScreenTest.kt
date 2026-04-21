package com.example.smple

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.smple.ui.auth.AuthViewModel
import com.example.smple.ui.auth.SignUpScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignUpScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val app: Application
        get() = InstrumentationRegistry.getInstrumentation()
            .targetContext.applicationContext as Application

    @Test
    fun signUpButton_isDisplayed() {
        val viewModel = AuthViewModel(app)
        composeTestRule.setContent {
            SignUpScreen(viewModel = viewModel, onSignUpSuccess = {}, onLoginClick = {})
        }
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
    }

    @Test
    fun confirmPasswordPlaceholder_isDisplayed() {
        val viewModel = AuthViewModel(app)
        composeTestRule.setContent {
            SignUpScreen(viewModel = viewModel, onSignUpSuccess = {}, onLoginClick = {})
        }
        composeTestRule.onNodeWithText("Confirm Password...").assertIsDisplayed()
    }
}
