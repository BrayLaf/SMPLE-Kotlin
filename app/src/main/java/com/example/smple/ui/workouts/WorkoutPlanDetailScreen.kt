package com.example.smple.ui.workouts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.AlertDialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smple.ui.theme.AppBackgroundGradient
import com.example.smple.ui.theme.TextDark

@Composable
fun WorkoutPlanDetailScreen(
    planId: String,
    viewModel: WorkoutViewModel,
    onWorkoutClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val plans by viewModel.plans.collectAsStateWithLifecycle()
    val workouts by viewModel.workouts.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(planId) {
        viewModel.loadWorkouts(planId)
    }

    val planName = plans.firstOrNull { it.id == planId }?.name ?: "Workout Plan"

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
                            text = "No workouts in this plan yet.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextDark.copy(alpha = 0.6f),
                            modifier = Modifier.padding(vertical = 12.dp),
                        )
                    }
                } else {
                    items(workouts) { workout ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .background(Color.White.copy(alpha = 0.30f), RoundedCornerShape(12.dp))
                                .clickable { onWorkoutClick(workout.id) }
                                .padding(horizontal = 20.dp, vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = workout.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextDark,
                                )
                                if (workout.content.isNotBlank()) {
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = workout.content,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextDark.copy(alpha = 0.6f),
                                        maxLines = 2,
                                    )
                                }
                            }
                            Text(
                                text = "Delete",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextDark.copy(alpha = 0.4f),
                                modifier = Modifier.clickable { viewModel.deleteWorkout(workout.id, planId) },
                            )
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showAddDialog = true }
                            .padding(vertical = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "New entry",
                            tint = TextDark.copy(alpha = 0.55f),
                            modifier = Modifier.size(22.dp),
                        )
                        Text(
                            text = "  New Workout Entry",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextDark.copy(alpha = 0.55f),
                        )
                    }
                }

                item { Spacer(Modifier.height(120.dp)) }
            }
        }
    }

    if (showAddDialog) {
        var entryName by remember { mutableStateOf("") }
        var entryNotes by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = {
                Text(
                    "New Workout Entry",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = entryName,
                        onValueChange = { entryName = it },
                        label = { Text("Name (e.g. Lower Body)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = entryNotes,
                        onValueChange = { entryNotes = it },
                        label = { Text("Exercises (e.g. squat 3x8)") },
                        minLines = 3,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.addWorkoutToPlan(planId, entryName, entryNotes)
                        showAddDialog = false
                    },
                    enabled = entryName.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                ) { Text("Add") }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel", color = TextDark)
                }
            },
        )
    }

    if (error != null) {
        AlertDialog(
            onDismissRequest = viewModel::clearError,
            title = { Text("Error", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold) },
            text = { Text(error!!, style = MaterialTheme.typography.bodyMedium) },
            confirmButton = {
                TextButton(onClick = viewModel::clearError) { Text("OK", color = TextDark) }
            },
        )
    }
}
