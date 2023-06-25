package com.example.ece_452_project.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ece_452_project.R
import com.example.ece_452_project.data.DummyPlace
import com.example.ece_452_project.data.Event
import com.example.ece_452_project.data.User
import com.example.ece_452_project.ui.components.Day
import com.example.ece_452_project.ui.components.DaysOfWeekTitle
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.LocalDateTime
import java.time.YearMonth

/**
 * Composable for the dashboard
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    user: User = User(),
    onCreateEventButtonClick: () -> Unit,
    onViewEventClick: () -> Unit
) {
    val viewModel: EventViewModel = viewModel()
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
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

            val user1 = User (
                schedule = mutableListOf(
                    Event(
                        start = LocalDateTime.of(2023, 6, 29, 7, 30),
                        end = LocalDateTime.of(2023, 6, 29, 10, 30)
                    ),
                    Event(
                        start = LocalDateTime.of(2023, 6, 29, 20, 30),
                        end = LocalDateTime.of(2023, 6, 29, 22, 0)
                    ),
                )
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

            if (user.schedule.isEmpty()) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                    text = stringResource(R.string.no_events),
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                // Row of dummy events for user
                user.schedule.forEach { event ->
                    OutlinedButton(
                        onClick = onViewEventClick,
                        border = BorderStroke(
                            width = 3.dp,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.fillMaxWidth().padding(all = 16.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                modifier = Modifier.weight(3f),
                                text = event.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.width(width = 155.dp))
                            Icon(
                                modifier = Modifier.size(35.dp).weight(1f),
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = ""
                            )
                        }
                    }
                }
            }



            Button(onClick = onCreateEventButtonClick,
                modifier= Modifier.padding(start = 16.dp, bottom = 16.dp).size(40.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
            ) {
                Icon(Icons.Default.Add ,contentDescription = "content description", modifier = Modifier.size(25.dp), tint = MaterialTheme.colorScheme.surface)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview(){
    DashboardScreen(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        onCreateEventButtonClick = { },
        onViewEventClick = {}
    )
}
