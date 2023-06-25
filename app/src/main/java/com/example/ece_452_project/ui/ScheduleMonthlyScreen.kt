package com.example.ece_452_project.ui

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
import com.example.ece_452_project.ui.components.DaysOfWeekTitle
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
) {

    val currentDate = remember { LocalDate.now() }
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(12) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(12) } // Adjust as needed
    val daysOfWeek = remember { daysOfWeek() } // Available from the library

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    val coroutineScope = rememberCoroutineScope()

    Column {

        CalendarHeader(
            onPrevButtonClicked = { coroutineScope.launch {
                state.scrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
            }},
            onNextButtonClicked = { coroutineScope.launch {
                state.scrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
            } },
            currentMonth = currentMonth
        )

        DaysOfWeekTitle(daysOfWeek = daysOfWeek)
        HorizontalCalendar(
            state = state,
            dayContent = { Day(it) },
            calendarScrollPaged = true,
            contentHeightMode = ContentHeightMode.Fill,
//            monthBody = monthBody,
//            monthHeader = { DaysOfWeekTitle(daysOfWeek = daysOfWeek )},
//            monthFooter = monthFooter,
        )
    }
}

@Composable
fun CalendarHeader(
    onPrevButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    currentMonth: YearMonth
){
    // Month header with previous and next month buttons
    Row(verticalAlignment = Alignment.CenterVertically){
        Button(
            shape = CircleShape,
            onClick =  onPrevButtonClicked
        ){
            Text(
                text = stringResource(R.string.left_arrow),
                fontSize = 22.sp
            )
        }
        Text(
            text = currentMonth.month.name,
            fontSize = 22.sp,
            modifier = Modifier.padding((16.dp))
        )
        Button(
            shape = CircleShape,
            onClick = onNextButtonClicked
        ){
            Text(
                text = stringResource(R.string.right_arrow),
                fontSize = 22.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleMonthlyPreview(){
    ScheduleMonthlyScreen(
    )
}
