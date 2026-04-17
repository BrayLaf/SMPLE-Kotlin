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

@Composable
fun WorkoutDetailScreen(
    planName: String,
    category: String,
    viewModel: WorkoutViewModel,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val exercises by viewModel.exercises.collectAsStateWithLifecycle()

    LaunchedEffect(category) { viewModel.loadExercises(category) }

    Box(modifier = modifier.fillMaxSize().background(AppBackgroundGradient)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
        ) {
            // Header plan name left, category + Edit right (no bar since Edit takes that slot)
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
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextDark,
                    )
                    Text(
                        text = "  Edit",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextDark.copy(alpha = 0.55f),
                        modifier = Modifier.clickable(onClick = onEditClick),
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .background(Color.White.copy(alpha = 0.30f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 20.dp, vertical = 16.dp),
                    ) {
                        exercises.forEach { exercise ->
                            Text(
                                text = exercise,
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextDark,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                            )
                        }
                    }
                }

                item { Spacer(Modifier.height(120.dp)) }
            }
        }
    }
}
