package com.example.ece_452_project.ui

// second page of new event creation - lets you pick time and place (initial options for discussion)
// save event here and START DISCUSSION

import android.annotation.SuppressLint
import java.util.Calendar
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventInfoScreen(
    modifier: Modifier = Modifier,
    onBackToFriendsClicked: () ->Unit,
    onTimeButtonClicked: () -> Unit,
    onPlaceButtonClicked: () -> Unit,
    onDeadlineChange: (String) -> Unit,
    onFinishButtonClicked: (LocalDateTime) -> Unit,
){
    val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    val current = LocalDateTime.now()
    val tmp = dateFormat.format(current)
    var deadlineDate by rememberSaveable { mutableStateOf(tmp) }
    // Button: go back to select friends button. Navigate to Select Friends view
    TextButton(
        onClick = onBackToFriendsClicked,
        modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                modifier = Modifier.size(42.dp),
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = ""
            )
            Text(
                text = stringResource(R.string.back_to_invite_friends),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(104.dp))
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            
            // Title: "Event Details"
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.event_details),
                style = MaterialTheme.typography.headlineSmall
            )
            
            // Button: to select event time. Navigates to Time selection view
            OutlinedButton(
                onClick = onTimeButtonClicked,
                border = BorderStroke(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth().padding(all = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp).weight(1f),
                        imageVector = Icons.Default.DateRange,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(width = 5.dp))
                    Text(
                        modifier = Modifier.weight(5f),
                        text = stringResource(R.string.select_time),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        modifier = Modifier.size(25.dp).weight(1f),
                        imageVector = Icons.Default.Edit,
                        contentDescription = ""
                    )
                }
            }

            // Button: to select event place. Navigates to place selection view
            OutlinedButton(
                onClick = onPlaceButtonClicked,
                border = BorderStroke(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth().padding(all = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp).weight(1f),
                        imageVector = Icons.Default.Place,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(width = 5.dp))
                    Text(
                        modifier = Modifier.weight(5f),
                        text = stringResource(R.string.select_place),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        modifier = Modifier.size(25.dp).weight(1f),
                        imageVector = Icons.Default.Edit,
                        contentDescription = ""
                    )
                }
            }

            // DatePicker: user select decision deadline
            val context = LocalContext.current
            val calendar = Calendar.getInstance()
            // Fetching current year, month and day
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
            val datePicker = DatePickerDialog(
                context,
                R.style.DatePickTheme,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                    deadlineDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                }, year, month, dayOfMonth
            )

            TextField(
                value = deadlineDate,
                onValueChange = onDeadlineChange,
                readOnly = true,
                singleLine = true,
                label = { Text(stringResource(R.string.deadline_date)) },
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 16.dp, end = 16.dp),
                interactionSource = remember { MutableInteractionSource() }
                    .also { interactionSource ->
                        LaunchedEffect(interactionSource) {
                            interactionSource.interactions.collect {
                                if (it is PressInteraction.Release) {
                                    datePicker.show()
                                }
                            }
                        }
                    },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            )

            Text(
                text = "Deadline for event participants to make suggestions",
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth().padding(start = 24.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))

        val sb = StringBuilder()
        var list1 = deadlineDate.split(" ")
        deadlineDate = list1[0]
        var list = deadlineDate.split("/")
        for (i in 0 until list.size) {
            if (list[i].length < 2) {
                sb.append("0").append(list[i])
            }
            else {
                sb.append(list[i])
            }
            if (list[i].length < 4) {
                sb.append("/")
            }
        }
        val current = LocalDateTime.now()
        var currentMin = current.minute.toString()
        if (current.minute < 10) {
            currentMin = "0$currentMin"
        }
        var currentHour = current.hour.toString()
        if (current.hour < 10) {
            currentHour = "0$currentHour"
        }
        sb.append(" $currentHour:$currentMin")
        val deadlineStr = sb.toString()
        val deadlineDateTime = LocalDateTime.parse(deadlineStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        Text(
            text = deadlineDateTime.toString()
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {onFinishButtonClicked(deadlineDateTime)}
        ) {
            Text(
                text = stringResource(R.string.start_discussion),
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventInfoPreview(){
    EventInfoScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        onBackToFriendsClicked = {},
        onTimeButtonClicked = {},
        onPlaceButtonClicked = {},
        onDeadlineChange = {new: String -> Unit},
        onFinishButtonClicked = {new: LocalDateTime -> Unit}
    )
}