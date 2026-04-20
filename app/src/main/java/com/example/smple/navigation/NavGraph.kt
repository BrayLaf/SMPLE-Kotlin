package com.example.smple.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smple.ui.auth.LoginScreen
import com.example.smple.ui.auth.OnboardingScreen
import com.example.smple.ui.home.HomeScreen
import com.example.smple.ui.home.HomeViewModel
import com.example.smple.ui.workouts.WorkoutDetailScreen
import com.example.smple.ui.workouts.WorkoutListScreen
//import com.example.smple.ui.workouts.WorkoutPlanDetailScreen
import com.example.smple.ui.workouts.WorkoutViewModel

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route,
        modifier = modifier,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel<HomeViewModel>(),
                onEntryClick = { /* TODO: navigate to entry detail when data layer is ready */ },
            )
        }

        composable(Screen.WorkoutList.route) {
            WorkoutListScreen(
                viewModel = viewModel<WorkoutViewModel>(),
                onPlanClick = { planName ->
                    navController.navigate(Screen.WorkoutPlanDetail.createRoute(planName))
                },
                onNewWorkout = { /* TODO: show add-plan dialog */ },
            )
        }

//        composable(Screen.WorkoutPlanDetail.route) { backStack ->
//            val planName = backStack.arguments?.getString("planName") ?: ""
//            WorkoutPlanDetailScreen(
//                planName = planName,
//                viewModel = viewModel<WorkoutViewModel>(),
//                onCategoryClick = { category ->
//                    navController.navigate(Screen.WorkoutDetail.createRoute(planName, category))
//                },
//                onNewPlan = { /* TODO: add new category */ },
//            )
//        }

        composable(Screen.WorkoutDetail.route) { backStack ->
            val planName = backStack.arguments?.getString("planName") ?: ""
            val category = backStack.arguments?.getString("category") ?: ""
            WorkoutDetailScreen(
                planName = planName,
                category = category,
                viewModel = viewModel<WorkoutViewModel>(),
                onEditClick = { /* TODO: enter edit mode */ },
            )
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onSignUpClick = { navController.navigate(Screen.SignUp.route) },
                onLoginClick = { navController.navigate(Screen.Login.route) },
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSignUpClick = { navController.navigate(Screen.SignUp.route) },
                onForgotPasswordClick = { navController.navigate(Screen.ForgotPassword.route) },
            )
        }
        composable(Screen.SignUp.route) { /* TODO */ }
        composable(Screen.ForgotPassword.route) { /* TODO */ }
        composable(Screen.Profile.route) { /* TODO: ProfileScreen */ }
    }
}
