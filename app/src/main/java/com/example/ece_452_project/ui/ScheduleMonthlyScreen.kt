package com.example.ece_452_project.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.R
import com.example.ece_452_project.data.DummyPlace
import com.example.ece_452_project.data.User
import com.example.ece_452_project.ui.components.Day
import com.kizitonwose.calendar.compose.ContentHeightMode
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale



/**
 * Composable for the dashboard
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleMonthlyScreen(
//    onPrevMonthButtonClicked: () -> Unit,
//    onNextMonthButtonClicked: () -> Unit

    user: User = User(),

) {

    val sharedEvents = user.schedule.filter { it.shared }
    val sharedDates = sharedEvents.map { it.start.toLocalDate() }

    val state = rememberCalendarState(
        startMonth = YearMonth.now(),
        endMonth = YearMonth.now(),
        firstVisibleMonth = YearMonth.now(),
        firstDayOfWeek = firstDayOfWeekFromLocale()
    )

    Card(
        modifier = Modifier.fillMaxSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        val sharedEvents = user.schedule.filter { it.shared }
        val sharedDates = sharedEvents.map { it.start.toLocalDate() }
        HorizontalCalendar(
            modifier = Modifier.padding(16.dp),
            contentHeightMode = ContentHeightMode.Fill,
            state = rememberCalendarState(
                startMonth = YearMonth.now().minusMonths(12),
                endMonth = YearMonth.now().plusMonths(12),
                firstVisibleMonth = YearMonth.now(),
                firstDayOfWeek = firstDayOfWeekFromLocale()
            ),
            dayContent = { day ->
                Day(day, isSelected = (day.date in sharedDates))
            },
            monthHeader = { month ->
                com.example.ece_452_project.ui.components.CalendarHeader(month = month)
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ScheduleMonthlyPreview(){
    ScheduleMonthlyScreen(
    )
}
