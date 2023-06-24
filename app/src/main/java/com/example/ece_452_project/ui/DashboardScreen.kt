package com.example.ece_452_project.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ece_452_project.R
import com.example.ece_452_project.data.DummyPlace
import com.example.ece_452_project.data.User
import com.example.ece_452_project.ui.components.Day
import com.example.ece_452_project.ui.components.DaysOfWeekTitle
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.YearMonth

/**
 * Composable for the dashboard
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    user: User = User()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.hello_comma) + " " + user.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.upcoming_meets),
                style = MaterialTheme.typography.headlineSmall
            )
            HorizontalCalendar(
                modifier = Modifier.padding(16.dp),
                state = rememberCalendarState(
                    startMonth = YearMonth.now().minusMonths(12),
                    endMonth = YearMonth.now().plusMonths(12),
                    firstVisibleMonth = YearMonth.now(),
                    firstDayOfWeek = firstDayOfWeekFromLocale()
                ),
                dayContent = { Day(it) },
                monthHeader = {month ->
                    val daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }
                    DaysOfWeekTitle(daysOfWeek = daysOfWeek)
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                text = stringResource(R.string.in_discussion),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                text = stringResource(R.string.no_events),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview(){
    DashboardScreen(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp))
}
