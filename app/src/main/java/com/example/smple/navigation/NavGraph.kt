package com.example.smple.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Onboarding.route) {
        composable(Screen.Onboarding.route) { /* TODO: OnboardingScreen() */ }
        composable(Screen.Login.route) { /* TODO: LoginScreen() */ }
        composable(Screen.SignUp.route) { /* TODO: SignUpScreen() */ }
        composable(Screen.ForgotPassword.route) { /* TODO: ForgotPasswordScreen() */ }
        composable(Screen.Home.route) { /* TODO: HomeScreen() */ }
        composable(Screen.WorkoutList.route) { /* TODO: WorkoutListScreen() */ }
        composable(Screen.WorkoutDetail.route) { /* TODO: WorkoutDetailScreen() */ }
        composable(Screen.Profile.route) { /* TODO: ProfileScreen() */ }
    }
}
