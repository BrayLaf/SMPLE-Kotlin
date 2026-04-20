package com.example.smple.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smple.domain.model.Entry
import com.example.smple.ui.theme.AppBackgroundGradient
import com.example.smple.ui.theme.GreenPrimary
import com.example.smple.ui.theme.TextDark
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onEntryClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentMonth by viewModel.currentMonth.collectAsStateWithLifecycle()
    val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    val entries by viewModel.entries.collectAsStateWithLifecycle()
    val trainingDays by viewModel.trainingDays.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    var showLogDialog by remember { mutableStateOf(false) }

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
                    text = "Home",
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
                        .background(Color.Black, RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)),
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
            ) {
                item {
                    CalendarSection(
                        currentMonth = currentMonth,
                        selectedDate = selectedDate,
                        trainingDays = trainingDays,
                        onPreviousMonth = viewModel::previousMonth,
                        onNextMonth = viewModel::nextMonth,
                        onDayClick = { date -> viewModel.selectDate(date) },
                    )
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val formatter = DateTimeFormatter.ofPattern("MMM d")
                        val label = if (selectedDate == LocalDate.now()) "Today's Gains"
                                    else selectedDate.format(formatter)
                        Text(
                            text = label,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = TextDark,
                        )
                        Text(
                            text = "+ Log",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextDark.copy(alpha = 0.6f),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable { showLogDialog = true },
                        )
                    }
                }

                if (entries.isEmpty()) {
                    item {
                        Text(
                            text = "No workouts logged.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextDark.copy(alpha = 0.5f),
                        )
                    }
                } else {
                    items(entries) { entry ->
                        EntryRow(
                            entry = entry,
                            onClick = { onEntryClick(entry.id) },
                            onDelete = { viewModel.deleteEntry(entry.id) },
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(120.dp)) }
            }
        }
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

    if (showLogDialog) {
        LogWorkoutDialog(
            date = selectedDate,
            onDismiss = { showLogDialog = false },
            onLog = { category, notes ->
                viewModel.logWorkout(selectedDate, category, notes)
                showLogDialog = false
            },
        )
    }
}

@Composable
private fun CalendarSection(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    trainingDays: Set<LocalDate>,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onDayClick: (LocalDate) -> Unit,
) {
    val today = LocalDate.now()
    val firstDay = currentMonth.atDay(1)
    val daysInMonth = currentMonth.lengthOfMonth()
    val startOffset = firstDay.dayOfWeek.value % 7

    val monthNames = arrayOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )
    val monthLabel = "${monthNames[currentMonth.monthValue - 1]} ${currentMonth.year}"

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onPreviousMonth) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous month", tint = TextDark)
            }
            Text(text = monthLabel, style = MaterialTheme.typography.bodyMedium, color = TextDark)
            IconButton(onClick = onNextMonth) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next month", tint = TextDark)
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa").forEach { label ->
                Text(
                    text = label,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextDark.copy(alpha = 0.55f),
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        val rows = (startOffset + daysInMonth + 6) / 7
        repeat(rows) { row ->
            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(7) { col ->
                    val day = row * 7 + col - startOffset + 1
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 3.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (day in 1..daysInMonth) {
                            val date = currentMonth.atDay(day)
                            val isToday = date == today
                            val isSelected = date == selectedDate && date != today
                            val hasEntry = date in trainingDays

                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .then(
                                        when {
                                            isToday -> Modifier
                                                .background(GreenPrimary, CircleShape)
                                                .border(2.dp, Color.White, CircleShape)
                                            isSelected -> Modifier
                                                .background(TextDark.copy(alpha = 0.12f), CircleShape)
                                            else -> Modifier
                                        }
                                    )
                                    .clickable { onDayClick(date) },
                                contentAlignment = Alignment.Center,
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = day.toString(),
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = if (isToday || isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isToday) Color.White else TextDark,
                                    )
                                    if (hasEntry && !isToday) {
                                        Box(
                                            modifier = Modifier
                                                .size(5.dp)
                                                .background(GreenPrimary, CircleShape),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EntryRow(entry: Entry, onClick: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.30f), RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = entry.title.ifBlank { entry.category },
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextDark,
            )
            if (entry.content.isNotBlank()) {
                Text(
                    text = entry.content,
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
            modifier = Modifier.clickable(onClick = onDelete),
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun LogWorkoutDialog(
    date: LocalDate,
    onDismiss: () -> Unit,
    onLog: (category: String, notes: String) -> Unit,
) {
    var selectedCategory by remember { mutableStateOf("Push") }
    var notes by remember { mutableStateOf("") }
    val formatter = DateTimeFormatter.ofPattern("MMMM d")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Log Workout — ${date.format(formatter)}",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            Column {
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextDark.copy(alpha = 0.6f),
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                Row {
                    listOf("Push", "Pull", "Legs").forEach { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat, style = MaterialTheme.typography.labelSmall) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = GreenPrimary,
                                selectedLabelColor = Color.White,
                            ),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes / exercises") },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onLog(selectedCategory, notes) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            ) {
                Text("Log Workout")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel", color = TextDark) }
        },
    )
}
