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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.example.smple.domain.model.Plan
import com.example.smple.ui.theme.AppBackgroundGradient
import com.example.smple.ui.theme.TextDark
import androidx.compose.material3.AlertDialog

@Composable
fun WorkoutListScreen(
    viewModel: WorkoutViewModel,
    onPlanClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val plans by viewModel.plans.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }

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
                if (plans.isEmpty()) {
                    item {
                        Text(
                            text = "No workout plans yet. Add one below.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextDark.copy(alpha = 0.6f),
                            modifier = Modifier.padding(vertical = 12.dp),
                        )
                    }
                } else {
                    items(plans) { plan ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .background(Color.White.copy(alpha = 0.30f), RoundedCornerShape(12.dp))
                                .clickable { onPlanClick(plan.id) }
                                .padding(horizontal = 20.dp, vertical = 20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = plan.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = TextDark,
                                modifier = Modifier.weight(1f),
                            )
                            Text(
                                text = "Delete",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextDark.copy(alpha = 0.4f),
                                modifier = Modifier.clickable { viewModel.deletePlan(plan.id) },
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

    if (showAddDialog) {
        var planName by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = {
                Text(
                    "New Workout Plan",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                OutlinedTextField(
                    value = planName,
                    onValueChange = { planName = it },
                    label = { Text("Plan name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.addPlan(planName)
                        showAddDialog = false
                    },
                    enabled = planName.isNotBlank(),
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
