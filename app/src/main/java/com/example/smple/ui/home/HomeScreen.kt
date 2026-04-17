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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smple.domain.model.Entry
import com.example.smple.ui.theme.GreenPrimary
import com.example.smple.ui.theme.TextDark
import java.time.LocalDate
import java.time.YearMonth

private val backgroundGradient = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to Color(0xFF34C759),
        0.82f to Color(0xFF34C759),
        1.0f to Color(0xFFFFFFFF),
    )
)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onEntryClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentMonth by viewModel.currentMonth.collectAsStateWithLifecycle()
    val recentEntries by viewModel.recentEntries.collectAsStateWithLifecycle()
    val trainingDays by viewModel.trainingDays.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize().background(backgroundGradient)) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
        ) {
            // Header lives outside the padded LazyColumn so the bar can touch the right edge
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
                // Bar aligned to the end — no end padding so it touches the screen edge
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
                item {
                    CalendarSection(
                        currentMonth = currentMonth,
                        trainingDays = trainingDays,
                        onPreviousMonth = viewModel::previousMonth,
                        onNextMonth = viewModel::nextMonth,
                    )
                }

                item {
                    Text(
                        text = "Today's Gains",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDark,
                        modifier = Modifier.padding(top = 30.dp, bottom = 10.dp),
                    )
                }

                if (recentEntries.isEmpty()) {
                    item {
                        Text(
                            text = "No workouts logged today.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextDark.copy(alpha = 0.5f),
                        )
                    }
                } else {
                    items(recentEntries) { entry ->
                        EntryRow(entry = entry, onClick = { onEntryClick(entry.id) })
                    }
                }

                item { Spacer(modifier = Modifier.height(120.dp)) }
            } // end LazyColumn
        } // end Column
    }
}

@Composable
private fun CalendarSection(
    currentMonth: YearMonth,
    trainingDays: Set<LocalDate>,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
) {
    val today = LocalDate.now()
    val firstDay = currentMonth.atDay(1)
    val daysInMonth = currentMonth.lengthOfMonth()
    val startOffset = firstDay.dayOfWeek.value % 7 // Sun=0 Mon=1 … Sat=6

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
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .then(
                                        if (isToday) Modifier
                                            .background(GreenPrimary, CircleShape)
                                            .border(2.dp, Color.White, CircleShape)
                                        else Modifier
                                    ),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = day.toString(),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isToday) Color.White else TextDark,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EntryRow(entry: Entry, onClick: () -> Unit) {
    Text(
        text = entry.content.lines().firstOrNull() ?: entry.title,
        style = MaterialTheme.typography.bodySmall,
        color = TextDark,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 6.dp),
    )
}
