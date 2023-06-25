package com.example.ece_452_project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.ui.theme.*
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.ece_452_project.R

/**
 * Composable for the time selection screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier
) {
    val currentDate = remember { LocalDate.now() }
    val startDate = remember { currentDate.minusDays(500) }
    val endDate = remember { currentDate.plusDays(500) }
    var selection by remember { mutableStateOf(currentDate) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val state = rememberWeekCalendarState(
                startDate = startDate,
                endDate = endDate,
                firstVisibleWeekDate = currentDate,
        )
        WeekCalendar(
                modifier = Modifier.background(color = Purple80),
                state = state,
                dayContent = { day ->
                    Day(day.date, isSelected = selection == day.date) { clicked ->
                        if (selection != clicked) {
                            selection = clicked
                        }
                    }
                },
        )
    }
}

private val dateFormatter = DateTimeFormatter.ofPattern("dd")

@Composable
private fun Day(date: LocalDate, isSelected: Boolean, onClick: (LocalDate) -> Unit) {
    Box(
            modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable { onClick(date) },
            contentAlignment = Alignment.Center,
    ) {
        Column(
                modifier = Modifier.padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                    text = date.dayOfWeek.toString(),
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Light,
            )
            Text(
                    text = dateFormatter.format(date),
                    fontSize = 14.sp,
                    color = if (isSelected) Pink80 else Color.White,
                    fontWeight = FontWeight.Bold,
            )
        }
        if (isSelected) {
            Box(
                    modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                            .background(PurpleGrey40)
                            .align(Alignment.BottomCenter),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SchedulePreview(){
    ScheduleScreen(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp))
}
