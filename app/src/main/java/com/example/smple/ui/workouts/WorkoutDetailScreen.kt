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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun WorkoutDetailScreen(
    entryId: Int,
    viewModel: WorkoutViewModel,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val entry by viewModel.selectedWorkout.collectAsStateWithLifecycle()
    var isEditing by remember { mutableStateOf(false) }
    var editTitle by remember { mutableStateOf("") }
    var editContent by remember { mutableStateOf("") }

    LaunchedEffect(entryId) {
        viewModel.loadWorkout(entryId)
    }

    LaunchedEffect(entry) {
        if (entry != null && !isEditing) {
            editTitle = entry!!.title
            editContent = entry!!.content
        }
    }

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
                    text = if (isEditing) "Edit Workout" else (entry?.title ?: "Workout"),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 24.dp),
                )
                Text(
                    text = if (isEditing) "Cancel" else "Edit",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextDark.copy(alpha = 0.55f),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 24.dp)
                        .clickable {
                            if (isEditing) {
                                isEditing = false
                                editTitle = entry?.title ?: ""
                                editContent = entry?.content ?: ""
                            } else {
                                isEditing = true
                            }
                        },
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
                } else if (isEditing) {
                    OutlinedTextField(
                        value = editTitle,
                        onValueChange = { editTitle = it },
                        label = { Text("Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = editContent,
                        onValueChange = { editContent = it },
                        label = { Text("Exercises") },
                        minLines = 4,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(Modifier.height(20.dp))
                    Button(
                        onClick = {
                            viewModel.updateWorkout(workout.id, editTitle.trim(), editContent.trim())
                            isEditing = false
                        },
                        enabled = editTitle.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        modifier = Modifier.fillMaxWidth(),
                    ) { Text("Save") }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .background(Color.White.copy(alpha = 0.30f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 20.dp, vertical = 16.dp),
                    ) {
                        if (workout.content.isNotBlank()) {
                            Text(
                                text = workout.content,
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextDark,
                            )
                        } else {
                            Text(
                                text = "No exercises noted.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextDark.copy(alpha = 0.5f),
                            )
                        }
                    }
                }

                Spacer(Modifier.height(120.dp))
            }
        }
    }
}
