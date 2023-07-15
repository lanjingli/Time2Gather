package com.example.ece_452_project.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.R
import com.example.ece_452_project.data.User
import com.example.ece_452_project.ui.components.CalendarHeader
import com.example.ece_452_project.ui.components.Day
import com.example.ece_452_project.ui.theme.SolidGreen
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
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
    onNewEventButtonClicked: () -> Unit
) {
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
            val sharedEvents = user.schedule.filter {it.shared}
            val sharedDates = sharedEvents.map {it.start.toLocalDate()}
            HorizontalCalendar(
                modifier = Modifier.padding(16.dp),
                state = rememberCalendarState(
                    startMonth = YearMonth.now().minusMonths(12),
                    endMonth = YearMonth.now().plusMonths(12),
                    firstVisibleMonth = YearMonth.now().plusMonths(1), /*TODO: Undo this after demo*/
                    firstDayOfWeek = firstDayOfWeekFromLocale()
                ),
                dayContent = {day ->
                    Day(day, isSelected = (day.date in sharedDates)) },
                monthHeader = {month ->
                    CalendarHeader(month = month)
                }
            )
            sharedEvents.forEach { event ->
                OutlinedButton(
                    onClick = { },
                    border = BorderStroke(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
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
                Text(
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                    text = stringResource(R.string.no_events),
                    style = MaterialTheme.typography.titleMedium
                )
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
        Spacer(modifier = Modifier.height(16.dp))

        //TODO: add View Schedule back
//        Row(horizontalArrangement = Arrangement.Center)
//        {
//            Button(
//                modifier = Modifier,
//                onClick = onViewScheduleButtonClicked,
//                colors = ButtonDefaults.buttonColors(containerColor = SolidGreen)
//            ) {
//                Row (verticalAlignment = Alignment.CenterVertically) {
//                    Icon(Icons.Default.DateRange, contentDescription = "Schedule")
//                    Text(
//                        modifier = Modifier.padding(2.dp),
//                        text = stringResource(R.string.view_schedule),
//                        fontSize = 16.sp,
//                        textAlign = TextAlign.Left
//                    )
//                }
//
//            }
//        }

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
        .padding(16.dp),
        onNewEventButtonClicked = {}
    )
}
