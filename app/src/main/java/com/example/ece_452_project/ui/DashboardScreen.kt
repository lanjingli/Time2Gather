package com.example.ece_452_project.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ece_452_project.R
import com.example.ece_452_project.data.Discussion
import com.example.ece_452_project.data.Event
import com.example.ece_452_project.data.User
import com.example.ece_452_project.generateAllEventsMap
import com.example.ece_452_project.ui.components.MonthDay
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.YearMonth
import java.time.format.DateTimeFormatter


/**
 * Composable for the dashboard
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    user: User = User(),
    onNewEventButtonClicked: () -> Unit,
    onEventClick: (Event) -> Unit,
    onDiscussionClick: (Discussion) -> Unit
) {

    var selectedDate by remember { mutableStateOf<CalendarDay?>(null) }

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

        // Calendar

        Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                text = stringResource(R.string.upcoming_meets),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            val sharedEvents = user.schedule.filter {it.shared}.sortedWith(compareBy { it.start })
            val sharedEventsMap = sharedEvents.groupBy {it.start.toLocalDate()}
            HorizontalCalendar(
                modifier = Modifier.padding(16.dp),
                state = rememberCalendarState(
                    startMonth = YearMonth.now().minusMonths(12),
                    endMonth = YearMonth.now().plusMonths(12),
                    firstVisibleMonth = YearMonth.now(),
                    firstDayOfWeek = firstDayOfWeekFromLocale()
                ),
                dayContent = { day ->

                    val dailyEvents = if (day.position == DayPosition.MonthDate) {
                        sharedEventsMap[day.date].orEmpty().map {it.start}
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

            sharedEvents.take(3).forEachIndexed {_, event ->
                OutlinedButton(
                    onClick = { onEventClick(event) },
                    border = BorderStroke(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier.weight(4f),
                            text = event.name + " - " + event.start.format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy")),
                            style = MaterialTheme.typography.titleMedium
                        )
                        //Spacer(modifier = Modifier.width(width = 155.dp))
                        Icon(
                            modifier = Modifier
                                .size(35.dp)
                                .weight(1f),
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = ""
                        )
                    }
                }

            }

            if (sharedEvents.isEmpty()){
                Text (
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp,),
                    text = stringResource(R.string.no_shared_events),
                    style = MaterialTheme.typography.titleMedium)
            }
            else{
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

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
            user.discussions.forEach{discussion ->
                OutlinedButton(
                    onClick = { onDiscussionClick(discussion) },
                    border = BorderStroke(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier.weight(4f),
                            text = discussion.name + " - " + discussion.deadline.format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy")),
                            style = MaterialTheme.typography.titleMedium
                        )
                        //Spacer(modifier = Modifier.width(width = 155.dp))
                        Icon(
                            modifier = Modifier
                                .size(35.dp)
                                .weight(1f),
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = ""
                        )
                    }
                }
            }
            if (user.discussions.isEmpty()) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                    text = stringResource(R.string.no_events),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            else{
                Spacer(modifier = Modifier.height(8.dp))
            }
            Button(onClick = onNewEventButtonClicked,
                modifier= Modifier
                    .padding(start = 16.dp, bottom = 16.dp)
                    .size(40.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "content description", modifier = Modifier.size(25.dp), tint = MaterialTheme.colorScheme.surface)
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
        onNewEventButtonClicked = {},
        onEventClick = { event: Event -> Unit},
        onDiscussionClick = { disc: Discussion -> Unit }
    )
}
