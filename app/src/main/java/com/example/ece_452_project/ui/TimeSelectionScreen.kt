package com.example.ece_452_project.ui

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.R
import com.example.ece_452_project.data.DummyData
import com.example.ece_452_project.data.User
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

enum class ScreenState(){
    Default,
    DateAdjust,
    SessionAdjust
}

@Composable
fun TimeSelectionScreen(
    currentUser: User,
    friends: List<User>,
    modifier: Modifier = Modifier,
    onBackToEventInfoClicked: () -> Unit,
    onDoneButtonClicked: (List<LocalDateTime>) -> Unit
){
    var screenState by remember {
        mutableStateOf(ScreenState.Default)
    }

    var dateState by remember {
        mutableStateOf(LocalDateTime.now())
    }
    val simpleDateFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    val dailyCalendarFormat = DateTimeFormatter.ofPattern("HH:mm")

    var durationState by remember {
        mutableStateOf(15)
    }

    if (screenState == ScreenState.Default) {
        val numCols = friends.size + 2
        val headerList = (0 until numCols).toList()
        val numList = (0 until numCols * 96).toList()
        val itemList: MutableList<String> = MutableList(numCols * 96) { "" }
        var dailyCalendarTime = LocalDateTime.of(dateState.year, dateState.month, dateState.dayOfMonth, 0, 0, 0)
        for (num in numList) {
            var result = "";
            if (num % numCols == 0) {
                if (num != 0) dailyCalendarTime = dailyCalendarTime.plusMinutes(15)
                result = if (isAvailable(currentUser, dailyCalendarTime, dailyCalendarTime.plusMinutes(15)) &&
                        isAvailable(friends, dailyCalendarTime, dailyCalendarTime.plusMinutes(15))) {
                    "G";
                } else {
                    "R";
                }
                result += 'T' + dailyCalendarFormat.format(dailyCalendarTime)
            } else {
                if (num % numCols == 1) {
                    if (!isAvailable(currentUser, dailyCalendarTime, dailyCalendarTime.plusMinutes(15))) {
                        result = "U"
                    }
                } else {
                    if (!isAvailable(friends[num % numCols - 2], dailyCalendarTime, dailyCalendarTime.plusMinutes(15))) {
                        result = "U"
                    }
                }
            }
            itemList[num] = result
        }

        Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
        ) {
            TextButton(
                    onClick = onBackToEventInfoClicked,
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp),
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
                            text = stringResource(R.string.back_to_event_details),
                            style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Box(
                    modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(15.dp))
                                .background(color = MaterialTheme.colorScheme.primary)
                ) {
                    Button(
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            onClick = {
                                dateState = dateState.minusDays(1)
                            }) {
                        Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                        )
                    }
                    TextButton(onClick = {screenState = ScreenState.DateAdjust}) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                    text = dateState.dayOfWeek.toString(),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 20.sp,
                                    color = Color.White
                            )
                            Text(
                                    text = simpleDateFormat.format(dateState),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 20.sp,
                                    color = Color.White
                            )
                        }
                    }
                    Button(
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            onClick = {
                                dateState = dateState.plusDays(1)
                            }) {
                        Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }

            LazyVerticalGrid(
                    columns = GridCells.Fixed(numCols),
                    modifier = Modifier.fillMaxWidth()
            ) {
                items(headerList) {
                    Box(modifier = Modifier
                            .border(
                                    BorderStroke(
                                            width = Dp.Hairline,
                                            color = Color.White
                                    )
                            )
                            .background(color = MaterialTheme.colorScheme.primary)
                            .fillMaxWidth()
                            .wrapContentSize()
                    ) {
                        Text(
                                color = Color.White,
                                text = when (it) {
                                    0 -> {
                                        "Time"
                                    }

                                    1 -> {
                                        currentUser.name
                                    }

                                    else -> {
                                        friends[it - 2].name
                                    }
                                }
                        )
                    }
                }
            }
            LazyVerticalGrid(
                    columns = GridCells.Fixed(numCols),
                    modifier = Modifier.fillMaxSize()
            ) {
                items(itemList) {
                    if (it == "U") {
                        Box(
                                modifier = Modifier
                                        .background(color = Color.Gray)
                                        .fillMaxWidth()
                        ) {
                            Text("")
                        }
                    } else if (it.length > 1) {
                        if (it.get(0) == 'R') {
                            Box(
                                    modifier = Modifier
                                            .border(
                                                    BorderStroke(
                                                            width = Dp.Hairline,
                                                            color = Color.White
                                                    )
                                            )
                                            .background(color = Color(0XFF800000))
                                            .fillMaxWidth()
                                            .wrapContentSize()
                            ) {
                                Text(
                                        color = Color.White,
                                        text = it.substringAfterLast('T')
                                )
                            }
                        } else {
                            TextButton(
                                    modifier = Modifier
                                            .border(
                                                    BorderStroke(
                                                            width = Dp.Hairline,
                                                            color = Color.White
                                                    )
                                            )
                                            .background(color = Color(0XFF006400))
                                            .fillMaxWidth()
                                            .wrapContentSize(),
                                    onClick = {
                                         dateState = onGreenButtonClick(
                                                time = dateState,
                                                strTime = it.substringAfterLast('T')
                                        )
                                        screenState = ScreenState.SessionAdjust
                                    }
                            ) {
                                Text(
                                        color = Color.White,
                                        text = it.substringAfterLast('T')
                                )
                            }
                        }
                    }
                }
            }
        }
    } else if (screenState == ScreenState.DateAdjust) {
        Column() {
            Row(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
            ) {
                Text(
                        text = "Select Date",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                )
            }
            Row(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    onClick = {dateState = dateState.minusYears(1)}
                ) {
                    Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = ""
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Year",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = dateState.year.toString(),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    onClick = {dateState = dateState.plusYears(1)}
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = ""
                    )
                }
            }
            Row(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        onClick = {dateState = dateState.minusMonths(1)}
                ) {
                    Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = ""
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                            text = "Month",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                            text = dateState.month.toString(),
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                    )
                }
                Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        onClick = {dateState = dateState.plusMonths(1)}
                ) {
                    Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = ""
                    )
                }
            }
            Row(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        onClick = {dateState = dateState.minusDays(1)}
                ) {
                    Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = ""
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                            text = "Day",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                            text = dateState.dayOfMonth.toString(),
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                    )
                }
                Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        onClick = {dateState = dateState.plusDays(1)}
                ) {
                    Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = ""
                    )
                }
            }
            Row(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                    top = 12.dp,
                                    start = 12.dp,
                                    end = 12.dp
                            ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0XFFC00000)),
                        onClick = {dateState = LocalDateTime.now()}
                ) {
                    Text(
                            text = "Reset",
                            fontSize = 20.sp,
                            color = Color.White
                    )
                }
            }
            Row(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                    top = 6.dp,
                                    start = 12.dp,
                                    end = 12.dp,
                                    bottom = 12.dp
                            ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    onClick = {screenState = ScreenState.Default}
                ) {
                    Text(
                            text = "Done",
                            fontSize = 20.sp,
                            color = Color.White
                    )
                }
            }
        }
    } else {
        Column() {
            Row(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
            ) {
                Text(
                        text = "Select Session Time",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                )
            }
            Column(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                        text = "${dateState.dayOfWeek}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                )
                Text(
                        text = "${simpleDateFormat.format(dateState)}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                )
            }
            Row(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        onClick = {dateState = dateState.minusMinutes(15)}
                ) {
                    Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = ""
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                            text = "Start Time",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                            text = dailyCalendarFormat.format(dateState),
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                    )
                }
                Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        onClick = {dateState = dateState.plusMinutes(15)}
                ) {
                    Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = ""
                    )
                }
            }
            Row(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        onClick = {
                            if (durationState < 15) 0
                            else durationState -= 15
                        }
                ) {
                    Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = ""
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                            text = "Duration",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                            text = formatDuration(durationState),
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                    )
                }
                Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        onClick = {durationState += 15}
                ) {
                    Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = ""
                    )
                }
            }
            Row(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                    top = 12.dp,
                                    start = 12.dp,
                                    end = 12.dp
                            ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        onClick = {screenState = ScreenState.Default}
                ) {
                    Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            Modifier.padding(end = 5.dp)
                    )
                    Text(
                            text = "Back",
                            fontSize = 20.sp,
                            color = Color.White
                    )
                }
            }
            Row(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                    top = 6.dp,
                                    start = 12.dp,
                                    end = 12.dp,
                                    bottom = 12.dp
                            ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        onClick = {onDoneButtonClicked(listOf(dateState, dateState.plusMinutes(durationState.toLong())))}
                ) {
                    Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            Modifier.padding(end = 5.dp)
                    )
                    Text(
                            text = "Done",
                            fontSize = 20.sp,
                            color = Color.White
                    )
                }
            }
        }
    }
}

fun isAvailable(
    user: User,
    startTime: LocalDateTime,
    endTime: LocalDateTime
) : Boolean {
    user.schedule.forEach { event ->
        if ((event.start < startTime && event.end > endTime) ||
            (event.start > startTime && event.end < endTime) ||
            (event.start < startTime && event.end > startTime) ||
            (event.start < endTime && event.end > endTime))
            return false
    }
    return true
}

fun isAvailable(
    users: List<User>,
    startTime: LocalDateTime,
    endTime: LocalDateTime
) : Boolean {
    users.forEach { user ->
        if (!isAvailable(user, startTime, endTime))
            return false
    }
    return true
}

fun onGreenButtonClick(
    time: LocalDateTime,
    strTime: String
) : LocalDateTime {
    return LocalDateTime.of(
            time.year,
            time.month,
            time.dayOfMonth,
            strTime.substringBefore(':').toInt(),
            strTime.substringAfterLast(':').toInt(),
            0
    )
}

fun formatDuration(
    time: Int
) : String {
    val hours = time / 60
    val minutes = time % 60
    return if (hours < 1) "$minutes min"
    else if (hours == 1) "$hours hr $minutes min"
    else "$hours hrs $minutes min"
}

@Preview(showBackground = true)
@Composable
fun TimeSelectionPreview(){
    TimeSelectionScreen(
            currentUser = DummyData.users.first { it.username == "alpha" },
            modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            friends = DummyData.users.filter {it.username != "alpha"},
            onBackToEventInfoClicked = {},
            onDoneButtonClicked = {selected: List<LocalDateTime> -> Unit}
    )
}

