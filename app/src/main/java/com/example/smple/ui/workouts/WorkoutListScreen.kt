package com.example.smple.ui.workouts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smple.ui.theme.AppBackgroundGradient
import com.example.smple.ui.theme.TextDark

@Composable
fun WorkoutListScreen(
    viewModel: WorkoutViewModel,
    onPlanClick: (String) -> Unit,
    onNewWorkout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val plans by viewModel.workoutPlans.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize().background(AppBackgroundGradient)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
        ) {
            // Header — same structure as HomeScreen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 18.dp),
            ) {
                Text(
                    text = "Workouts",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 24.dp),
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(width = 80.dp, height = 10.dp)
                        .background(Color.Black, RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp))
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
            ) {
                items(plans) { plan ->
                    Text(
                        text = plan,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextDark,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .background(Color.White.copy(alpha = 0.30f), RoundedCornerShape(12.dp))
                            .clickable { onPlanClick(plan) }
                            .padding(horizontal = 20.dp, vertical = 20.dp),
                    )
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onNewWorkout)
                            .padding(vertical = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "New workout",
                            tint = TextDark.copy(alpha = 0.55f),
                            modifier = Modifier.size(22.dp),
                        )
                        Text(
                            text = "  New Workout",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextDark.copy(alpha = 0.55f),
                        )
                    }
                }

                item { Spacer(Modifier.height(120.dp)) }
            }
        }
    }
}
