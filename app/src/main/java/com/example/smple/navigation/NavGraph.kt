package com.example.smple.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smple.ui.home.HomeScreen
import com.example.smple.ui.home.HomeViewModel

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel<HomeViewModel>(),
                onEntryClick = { entryId ->
                    navController.navigate(Screen.WorkoutDetail.createRoute(entryId))
                },
            )
        }
        composable(Screen.Onboarding.route) { /* TODO */ }
        composable(Screen.Login.route) { /* TODO */ }
        composable(Screen.SignUp.route) { /* TODO */ }
        composable(Screen.ForgotPassword.route) { /* TODO */ }
        composable(Screen.WorkoutList.route) { /* TODO: WorkoutListScreen */ }
        composable(Screen.WorkoutDetail.route) { /* TODO: WorkoutDetailScreen */ }
        composable(Screen.Profile.route) { /* TODO: ProfileScreen */ }
    }
}
