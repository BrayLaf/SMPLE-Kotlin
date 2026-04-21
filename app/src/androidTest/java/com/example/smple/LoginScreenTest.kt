package com.example.smple

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.smple.ui.auth.AuthViewModel
import com.example.smple.ui.auth.LoginScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val app: Application
        get() = InstrumentationRegistry.getInstrumentation()
            .targetContext.applicationContext as Application

    @Test
    fun emailPlaceholder_isDisplayed() {
        val viewModel = AuthViewModel(app)
        composeTestRule.setContent {
            LoginScreen(viewModel = viewModel, onLoginSuccess = {}, onSignUpClick = {})
        }
        composeTestRule.onNodeWithText("Email...").assertIsDisplayed()
    }

    @Test
    fun passwordPlaceholder_isDisplayed() {
        val viewModel = AuthViewModel(app)
        composeTestRule.setContent {
            LoginScreen(viewModel = viewModel, onLoginSuccess = {}, onSignUpClick = {})
        }
        composeTestRule.onNodeWithText("Password...").assertIsDisplayed()
    }

    @Test
    fun loginButton_isDisplayed() {
        val viewModel = AuthViewModel(app)
        composeTestRule.setContent {
            LoginScreen(viewModel = viewModel, onLoginSuccess = {}, onSignUpClick = {})
        }
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
    }
}
