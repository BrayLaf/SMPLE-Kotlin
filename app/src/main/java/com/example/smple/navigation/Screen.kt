package com.example.smple.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object ForgotPassword : Screen("forgot_password")
    object Home : Screen("home")
    object WorkoutList : Screen("workout_list")
    object WorkoutPlanDetail : Screen("workout_plan/{planName}") {
        fun createRoute(planName: String) = "workout_plan/$planName"
    }
    object WorkoutDetail : Screen("workout_detail/{planName}/{category}") {
        fun createRoute(planName: String, category: String) = "workout_detail/$planName/$category"
    }
    object Profile : Screen("profile")
}
