package com.example.smple.ui.workouts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smple.ui.theme.AppBackgroundGradient
import com.example.smple.ui.theme.TextDark
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun WorkoutPlanDetailScreen(
    planId: String,
    viewModel: WorkoutViewModel,
    onWorkoutClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val plans by viewModel.plans.collectAsStateWithLifecycle()
    val workouts by viewModel.workouts.collectAsStateWithLifecycle()

    LaunchedEffect(planId) {
        viewModel.loadWorkouts(planId)
    }

    val planName = plans.firstOrNull { it.id == planId }?.name ?: "Workout Plan"
    val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")

    Box(modifier = modifier.fillMaxSize().background(AppBackgroundGradient)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 18.dp),
            ) {
                Text(
                    text = planName,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 24.dp),
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
            ) {
                if (workouts.isEmpty()) {
                    item {
                        Text(
                            text = "No workouts logged for this plan yet.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextDark.copy(alpha = 0.6f),
                            modifier = Modifier.padding(vertical = 12.dp),
                        )
                    }
                } else {
                    items(workouts) { workout ->
                        val dateLabel = runCatching {
                            Instant.ofEpochMilli(workout.createdAt)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                                .format(formatter)
                        }.getOrDefault("")

                        Text(
                            text = buildString {
                                append(workout.title.ifBlank { "Workout" })
                                if (dateLabel.isNotBlank()) {
                                    append(" • ")
                                    append(dateLabel)
                                }
                                if (workout.content.isNotBlank()) {
                                    append("\n")
                                    append(workout.content)
                                }
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = TextDark,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .background(Color.White.copy(alpha = 0.30f), RoundedCornerShape(12.dp))
                                .clickable { onWorkoutClick(workout.id) }
                                .padding(horizontal = 20.dp, vertical = 20.dp),
                        )
                    }
                }

                item { Spacer(Modifier.height(120.dp)) }
            }
        }
    }
}
