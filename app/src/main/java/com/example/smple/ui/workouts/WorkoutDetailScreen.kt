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
fun WorkoutDetailScreen(
    entryId: Int,
    viewModel: WorkoutViewModel,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val entry by viewModel.selectedWorkout.collectAsStateWithLifecycle()

    LaunchedEffect(entryId) {
        viewModel.loadWorkout(entryId)
    }

    val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy • h:mm a")

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
                    text = entry?.title ?: "Workout",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 24.dp),
                )
                Text(
                    text = "Edit",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextDark.copy(alpha = 0.55f),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 24.dp)
                        .clickable(onClick = onEditClick),
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
            ) {
                val workout = entry
                if (workout == null) {
                    Text(
                        text = "Workout not found.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextDark.copy(alpha = 0.6f),
                        modifier = Modifier.padding(vertical = 12.dp),
                    )
                } else {
                    val dateLabel = runCatching {
                        Instant.ofEpochMilli(workout.createdAt)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
                            .format(formatter)
                    }.getOrDefault("")

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .background(Color.White.copy(alpha = 0.30f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 20.dp, vertical = 16.dp),
                    ) {
                        if (dateLabel.isNotBlank()) {
                            Text(
                                text = dateLabel,
                                style = MaterialTheme.typography.labelMedium,
                                color = TextDark.copy(alpha = 0.6f),
                            )
                        }
                        if (workout.content.isNotBlank()) {
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = workout.content,
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextDark,
                            )
                        }
                    }
                }

                Spacer(Modifier.height(120.dp))
            }
        }
    }
}
