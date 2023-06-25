package com.example.ece_452_project.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.ece_452_project.data.User
import com.example.ece_452_project.ui.components.Day
import com.example.ece_452_project.ui.components.DaysOfWeekTitle
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.YearMonth
import java.util.Calendar


/**
 * Composable for the initial sign up screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventPreferencesScreen(
    modifier: Modifier = Modifier,
    user: User = User(),
    onBackToSettingClick: () -> Unit,
    onEditEventTimePreferencesClick: () -> Unit,
    onEditEventLocationPreferencesClick: () -> Unit,
    onSaveEventPreferencesClick: () -> Unit,
) {


        IconButton(
            onClick = {
                onBackToSettingClick
            },
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 32.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                //modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = ""
                )
                //Spacer(modifier = Modifier.width(width = 8.dp))
                Text(
                    text = "Back to Settings",
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
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.event_preferences),
                style = MaterialTheme.typography.headlineSmall
            )
            OutlinedButton(
                onClick = onEditEventTimePreferencesClick,
                border = BorderStroke(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth().padding(all = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = Icons.Default.DateRange,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(width = 10.dp))
                    Text(
                        text = stringResource(R.string.event_time_preferences),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(width = 155.dp))
                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = Icons.Default.Edit,
                        contentDescription = ""
                    )
                }
            }
            OutlinedButton(
                onClick = onEditEventLocationPreferencesClick,
                border = BorderStroke(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth().padding(all = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = Icons.Default.Place,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(width = 10.dp))
                    Text(
                        text = stringResource(R.string.event_location_preferences),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(width = 150.dp))
                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = Icons.Default.Edit,
                        contentDescription = ""
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSaveEventPreferencesClick
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
fun EventPreferencesPreview() {
    EventPreferencesScreen(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        onBackToSettingClick = { },
        onEditEventTimePreferencesClick = { },
        onEditEventLocationPreferencesClick = { },
        onSaveEventPreferencesClick = {}
    )
}

