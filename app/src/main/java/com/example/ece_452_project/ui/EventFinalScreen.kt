package com.example.ece_452_project.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.R
import com.example.ece_452_project.data.Event
import com.example.ece_452_project.data.User
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventFinalScreen(
    modifier: Modifier = Modifier,
    user: User = User(),
    event: Event = Event(),
    friends: List<User> = listOf<User>(),
    onFinishButtonClicked: (Boolean) -> Unit,
    onDoneButtonClicked: () -> Unit,
){
    var selected by rememberSaveable { mutableStateOf(false)}

    val start = event.start
    val end = event.end
    var startMin = start.minute.toString()
    var endMin = end.minute.toString()
    var startStr = intToMonth(start.monthValue) + " " + start.dayOfMonth.toString() + ", " + start.year.toString()

    if (start.minute < 10) {
        startMin = "0$startMin"
    }

    if (end.minute < 10) {
       endMin = "0$endMin"
    }

    var startTime = start.hour.toString() + ":" + startMin
    var endTime = end.hour.toString() + ":" + endMin

    var listFriend = mutableListOf<String>()
    friends.forEach { friend ->
        listFriend.add(friend.username)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(96.dp))
        Text(
            text = "Plans for " + event.name + " has been finalized.",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                modifier = Modifier
                    .size(36.dp)
                    .weight(1f),
                imageVector = Icons.Default.Place,
                contentDescription = ""
            )
            Text(
                modifier = Modifier.weight(2f),
                text = event.location,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                modifier = Modifier
                    .size(36.dp)
                    .weight(1f),
                painter = painterResource(R.drawable.baseline_today_24),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.weight(2f),
                text = startStr,
                style = MaterialTheme.typography.titleMedium
            )

        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                modifier = Modifier
                    .size(36.dp)
                    .weight(1f),
                painter = painterResource(R.drawable.baseline_access_time_filled_24),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.weight(2f),
                text = "$startTime-$endTime",
                style = MaterialTheme.typography.titleMedium
            )

        }
        Spacer(modifier = Modifier.height(32.dp))
        if (event.eventOwner != user.username) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selected,
                    onCheckedChange = { checked_ ->
                        selected = checked_
                    }
                )

                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "I plan to participate in this finalized event",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {onFinishButtonClicked(selected)}
            ) {
                Text(
                    text = stringResource(R.string.finalize),
                    fontSize = 16.sp
                )
            }
        } else {
            if (event.attend.isNotEmpty()) {
                selected = true
                Text(
                    text = "The following users have confirmed to attend:",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Left
                )
                event.attend.forEach { user ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ){
                        Checkbox(
                            checked = selected,
                            onCheckedChange = {},
                            enabled = false
                        )
                        Text(text = user)
                    }
                }
            }
            var tmpList = listFriend.minus(event.attend)
            if (tmpList.isNotEmpty()) {
                selected = false

                Text(
                    text = "Pending users:",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Left
                )
                tmpList.forEach { user ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ){
                        Checkbox(
                            checked = selected,
                            onCheckedChange = {},
                            enabled = false
                        )
                        Text(text = user)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onDoneButtonClicked
            ) {
                Text(
                    text = stringResource(R.string.done),
                    fontSize = 16.sp
                )
            }
        }
    }
}

fun intToMonth(month: Int): String {
    when (month) {
        1 -> return "Jan"
        2 -> return "Feb"
        3 -> return "Mar"
        4 -> return "Apr"
        5 -> return "May"
        6 -> return "Jun"
        7 -> return "Jul"
        8 -> return "Aug"
        9 -> return "Sep"
        10 -> return "Oct"
        11 -> return "Nov"
        12 -> return "Dec"
        else -> {
            return "error"
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventFinalPreview(){
    EventFinalScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        onFinishButtonClicked = {new: Boolean -> Unit},
        onDoneButtonClicked = {},
    )
}