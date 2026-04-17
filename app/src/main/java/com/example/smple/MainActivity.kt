package com.example.smple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smple.navigation.NavGraph
import com.example.smple.navigation.Screen
import com.example.smple.ui.theme.GreenPrimary
import com.example.smple.ui.theme.SMPLETheme
import com.example.smple.ui.theme.TextDark

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SMPLETheme {
                SmpleAppUI()
            }
        }
    }
}
@Preview
@Composable
private fun SmpleAppUI() {
    val navController = rememberNavController()
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route

    val mainRoutes = setOf(
        Screen.Home.route,
        Screen.WorkoutList.route,
        Screen.WorkoutPlanDetail.route,
        Screen.WorkoutDetail.route,
        Screen.Profile.route,
    )

    Scaffold(
        containerColor = Color(0xFF34C759),
        bottomBar = {
            if (currentRoute in mainRoutes) {
                Column {
                    SmpleBottomBar(navController = navController, currentRoute = currentRoute)
                    // Home indicator bar in the system nav area
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .navigationBarsPadding()
                            .padding(bottom = 10.dp),
                        contentAlignment = Alignment.TopStart,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 80.dp, height = 10.dp)
                                .background(Color.Black, RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp))
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavGraph(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}

private data class NavItem(val icon: ImageVector, val route: String, val label: String)

@Composable
private fun SmpleBottomBar(navController: NavHostController, currentRoute: String?) {
    val items = listOf(
        NavItem(Icons.Default.FitnessCenter, Screen.WorkoutList.route, "Workouts"),
        NavItem(Icons.Default.Home, Screen.Home.route, "Home"),
        NavItem(Icons.Default.Person, Screen.Profile.route, "Profile"),
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp,
        modifier = Modifier.height(80.dp),
    ) {
        val workoutRoutes = setOf(
            Screen.WorkoutList.route,
            Screen.WorkoutPlanDetail.route,
            Screen.WorkoutDetail.route,
        )
        items.forEach { item ->
            val selected = if (item.route == Screen.WorkoutList.route) {
                currentRoute in workoutRoutes
            } else {
                currentRoute == item.route
            }
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (selected) GreenPrimary else TextDark,
                        modifier = Modifier.size(36.dp),
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = GreenPrimary,
                    unselectedIconColor = TextDark,
                ),
            )
        }
    }
}
