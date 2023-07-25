package com.example.ece_452_project.ui

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.R
import com.example.ece_452_project.data.Event
import com.example.ece_452_project.data.User
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventOptionScreen(
    modifier: Modifier = Modifier,
    user: User = User(),
    event: Event = Event(),
    onTimeButtonClicked: () -> Unit,
    onPlaceButtonClicked: () -> Unit,
    onFinishButtonClicked: () -> Unit,
){
    val current = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formatted = current.format(formatter)
    val sb = StringBuilder()
    var list = event.deadline.split("/")
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
    val deadlineStr = sb.toString()
    val sdf = SimpleDateFormat("MM/dd/yyyy")
    val dateOne: Date = sdf.parse(formatted)
    val dateTwo: Date = sdf.parse(deadlineStr)
    val cmp = dateOne.compareTo(dateTwo)
    var butEnabled = false
    var butBorderColor = Color(0xFFBDBDBD)
    // Date has not expired
    if (cmp < 0) {
        butBorderColor = MaterialTheme.colorScheme.primary
        butEnabled = true
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
                text = event.name,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = event.description,
                style = MaterialTheme.typography.titleMedium
            )
            OutlinedButton(
                enabled = butEnabled,
                onClick = onTimeButtonClicked,
                border = BorderStroke(
                    width = 3.dp,
                    color = butBorderColor
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
                        text = stringResource(R.string.timeslot_ranking),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        modifier = Modifier.size(25.dp).weight(1f),
                        imageVector = Icons.Default.Edit,
                        contentDescription = ""
                    )
                }
            }
            OutlinedButton(
                enabled = butEnabled,
                onClick = onPlaceButtonClicked,
                border = BorderStroke(
                    width = 3.dp,
                    color = butBorderColor
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
                        text = stringResource(R.string.location_ranking),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        modifier = Modifier.size(25.dp).weight(1f),
                        imageVector = Icons.Default.Edit,
                        contentDescription = ""
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            enabled = !butEnabled,
            modifier = Modifier.fillMaxWidth(),
            onClick = onFinishButtonClicked
        ) {
            Text(
                text = "Final Decision",
                fontSize = 16.sp
            )
        }
        Text(
            modifier = Modifier.padding(4.dp),
            text = "Deadline for Final Decisions is: \n" + event.deadline,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EventOptionPreview(){
    EventOptionScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        onTimeButtonClicked = {},
        onPlaceButtonClicked = {},
        onFinishButtonClicked = {}
    )
}