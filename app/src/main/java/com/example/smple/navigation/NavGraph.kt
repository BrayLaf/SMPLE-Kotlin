package com.example.smple.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smple.SmpleApplication
import com.example.smple.ui.auth.AuthViewModel
import com.example.smple.ui.auth.ForgotPasswordScreen
import com.example.smple.ui.auth.LoginScreen
import com.example.smple.ui.auth.OnboardingScreen
import com.example.smple.ui.auth.SignUpScreen
import com.example.smple.ui.home.HomeScreen
import com.example.smple.ui.home.HomeViewModel
import com.example.smple.ui.profile.ProfileScreen
import com.example.smple.ui.profile.ProfileViewModel
import com.example.smple.ui.workouts.WorkoutDetailScreen
import com.example.smple.ui.workouts.WorkoutListScreen
import com.example.smple.ui.workouts.WorkoutPlanDetailScreen
import com.example.smple.ui.workouts.WorkoutViewModel

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val startDestination = remember {
        val app = context.applicationContext as SmpleApplication
        if (app.authRepository.getCurrentUser() != null) Screen.Home.route
        else Screen.Onboarding.route
    }

    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel<HomeViewModel>(),
                onEntryClick = { /* TODO: navigate to entry detail */ },
            )
        }

        composable(Screen.WorkoutList.route) {
            WorkoutListScreen(
                viewModel = viewModel<WorkoutViewModel>(),
                onPlanClick = { planName ->
                    navController.navigate(Screen.WorkoutPlanDetail.createRoute(planName))
                },
            )
        }

        composable(Screen.WorkoutPlanDetail.route) { backStack ->
            val planName = backStack.arguments?.getString("planName") ?: ""
            WorkoutPlanDetailScreen(
                planName = planName,
                viewModel = viewModel<WorkoutViewModel>(),
                onCategoryClick = { category ->
                    navController.navigate(Screen.WorkoutDetail.createRoute(planName, category))
                },
                onNewPlan = { /* TODO: add new category */ },
            )
        }

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
                viewModel = authViewModel,
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

        composable(Screen.SignUp.route) {
            SignUpScreen(
                viewModel = authViewModel,
                onSignUpSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onLoginClick = { navController.navigate(Screen.Login.route) },
            )
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                viewModel = authViewModel,
                onBackClick = { navController.popBackStack() },
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                viewModel = viewModel<ProfileViewModel>(),
                onSignedOut = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}
