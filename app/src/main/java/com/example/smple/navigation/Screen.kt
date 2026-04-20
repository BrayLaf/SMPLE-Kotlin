package com.example.smple.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object ForgotPassword : Screen("forgot_password")
    object Home : Screen("home")
    object WorkoutList : Screen("workout_list")
    object WorkoutPlanDetail : Screen("workout_plan/{planId}") {
        fun createRoute(planId: String) = "workout_plan/$planId"
    }
    object WorkoutDetail : Screen("workout_detail/{entryId}") {
        fun createRoute(entryId: Int) = "workout_detail/$entryId"
    }
    object Profile : Screen("profile")
}
