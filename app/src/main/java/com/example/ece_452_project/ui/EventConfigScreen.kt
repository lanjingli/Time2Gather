package com.example.ece_452_project.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.R
import com.example.ece_452_project.data.Event
import com.example.ece_452_project.data.User
import java.util.Calendar


/**
 * Composable for the initial sign up screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventConfigScreen(
    modifier: Modifier = Modifier,
    user: User = User(),
    event: Event = Event(),
    eventNameText: String = "",
    eventDescText: String = "",
    decisionDeadlineText: String = "",
    timeToAllocText: String = "",
    startDateText: String = "",
    endDateText: String = "",
    onEventNameChange: (String) -> Unit,
    onEventDescChange: (String) -> Unit,
    onDecisionDeadlineChange: (String) -> Unit,
    onTimeToAllocChange: (String) -> Unit,
    onStartDateChange: (String) -> Unit,
    onInviteFriendsClick: () -> Unit,
    onSaveEventSettingClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = eventNameText + event.name,
            onValueChange = onEventNameChange,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
            label = { Text(stringResource(R.string.event_name)) },
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = eventDescText,
            onValueChange = onEventDescChange,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
            label = { Text(stringResource(R.string.event_description)) },
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = decisionDeadlineText,
            onValueChange = onDecisionDeadlineChange,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
            label = { Text(stringResource(R.string.decision_deadline)) },
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = timeToAllocText,
            onValueChange = onTimeToAllocChange,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
            label = { Text(stringResource(R.string.time_to_allocate)) },
        )

        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        var startDate by remember { mutableStateOf("") }
        // Fetching current year, month and day
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
        val datePicker = DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                startDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            }, year, month, dayOfMonth
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = startDate,
            onValueChange = { onStartDateChange },
            readOnly = true,
            singleLine = true,
            label = { Text(stringResource(R.string.start_date)) },
            modifier = Modifier.fillMaxWidth(),
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

        val context2 = LocalContext.current
        var endDateText by remember { mutableStateOf("") }
        val datePicker2 = DatePickerDialog(
            context2,
            { _: DatePicker, selectedYear2: Int, selectedMonth2: Int, selectedDayOfMonth2: Int ->
                endDateText = "$selectedDayOfMonth2/${selectedMonth2 + 1}/$selectedYear2"
            }, year, month, dayOfMonth
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = endDateText,
            onValueChange = { },
            readOnly = true,
            singleLine = true,
            label = { Text(stringResource(R.string.end_date)) },
            modifier = Modifier.fillMaxWidth(),
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                datePicker2.show()
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
        Spacer(modifier = Modifier.height(16.dp))
        //Share with friends
        Button(
            onClick = onInviteFriendsClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(10.dp),
                    imageVector = Icons.Default.Share,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(width = 15.dp))
                Text(
                    text = stringResource(R.string.invite_friends),
                    fontSize = 16.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSaveEventSettingClick
        ) {
            Text(
                text = stringResource(R.string.save),
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventConfigPreview() {
    EventConfigScreen(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        onEventNameChange = { new: String -> Unit },
        onEventDescChange = { new: String -> Unit },
        onDecisionDeadlineChange = { new: String -> Unit },
        onTimeToAllocChange = { new: String -> Unit },
        onStartDateChange = {},
        onInviteFriendsClick = {},
        onSaveEventSettingClick = { }
    )
}

