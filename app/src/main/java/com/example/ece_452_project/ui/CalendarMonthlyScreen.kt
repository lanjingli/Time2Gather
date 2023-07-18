package com.example.ece_452_project.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ece_452_project.data.DummyData
import com.example.ece_452_project.data.Event
import com.example.ece_452_project.data.User
import com.example.ece_452_project.generateAllEventsMap
import com.example.ece_452_project.ui.components.rememberFirstCompletelyVisibleMonth
import com.example.ece_452_project.ui.components.MonthDay
import com.example.ece_452_project.ui.theme.SolidGreen
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

/**
 * Composable for the dashboard
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarMonthlyScreen(

    user: User = User(),

) {

    val events = generateAllEventsMap(user).groupBy {it.start.toLocalDate()}

    val sharedEvents = user.schedule.filter { it.shared }
    val sharedDates = sharedEvents.map { it.start.toLocalDate() }

    val state = rememberCalendarState(
        startMonth = YearMonth.now().minusMonths(12),
        endMonth = YearMonth.now().plusMonths(12),
        firstVisibleMonth = YearMonth.now(),
        firstDayOfWeek = firstDayOfWeekFromLocale()
    )

    var selectedDate by remember { mutableStateOf<CalendarDay?>(null) }

    val eventsOnSelectedDate = remember {
        derivedStateOf {
            val date = selectedDate?.date

            if (date == null) emptyList() else events[date].orEmpty()
        }
    }

    Card(
        modifier = Modifier.fillMaxSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {

        val coroutineScope = rememberCoroutineScope()
        val visibleMonth = rememberFirstCompletelyVisibleMonth(state)

        LaunchedEffect(visibleMonth){ selectedDate = null}

        HorizontalCalendar(
            modifier = Modifier.padding(16.dp),
            state = state,
            dayContent = { day ->

                val dailyEvents = if (day.position == DayPosition.MonthDate) {
                    events[day.date].orEmpty().map {it.start}
                } else {
                    emptyList();
                }
                MonthDay(day = day, isSelected = selectedDate == day, dailyEvents = dailyEvents,
                ) { clicked ->
                    selectedDate = clicked

                }
            },
            monthHeader = { month ->
                com.example.ece_452_project.ui.components.CalendarHeader(month = month)
            }
        )

        Spacer(Modifier.padding(20.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items = eventsOnSelectedDate.value) { event ->
                EventInformation(event)
            }
        }

    }
}


@Composable
private fun LazyItemScope.EventInformation(event: Event) {
    val sb = StringBuilder()
    sb.append(event.start.format(DateTimeFormatter.ofPattern("hh:mm")));
    sb.append(" to ");
    sb.append(event.end.format(DateTimeFormatter.ofPattern("hh:mm")));


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            modifier = Modifier.weight(4f),
            text = event.name + " - " + event.start.format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy")),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            modifier = Modifier.weight(4f),
            text = String(sb),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarMonthlyPreview(){
    CalendarMonthlyScreen(
    )
}
