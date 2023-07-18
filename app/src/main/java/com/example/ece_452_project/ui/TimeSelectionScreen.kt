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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.R
import com.example.ece_452_project.data.DummyData
import com.example.ece_452_project.data.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TimeSelectionScreen(
    currentUser: User,
    friends: List<User>,
    modifier: Modifier = Modifier,
    onBackToEventInfoClicked: () -> Unit,
    onNextButtonClicked: (List<LocalDateTime>) -> Unit
){
    var selected by rememberSaveable { mutableStateOf(List<LocalDateTime>(2){LocalDateTime.now()})}

    var dateState by remember {
        mutableStateOf(LocalDateTime.now())
    }
    val simpleDateFormat = DateTimeFormatter.ofPattern("EE MMM dd, yyyy")

    val numCols = friends.size + 2
    val headerList = (0 until numCols).toList()
    val numList = (0 until numCols * 96).toList()
    val itemList: MutableList<String> = MutableList(numCols * 96) { "" }
    val itemsList = (0 until numCols * 96).toList()
    val dailyCalendarFormat = DateTimeFormatter.ofPattern("HH:mm")
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
    ){
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
                            .height(30.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp))
                            .background(color = MaterialTheme.colorScheme.primary)
            ) {
                Button(
                        modifier = Modifier.fillMaxHeight(),
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
                Text(
                        text = "${simpleDateFormat.format(dateState)}",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 24.sp,
                        color = Color.White
                )
                Button(
                        modifier = Modifier.fillMaxHeight(),
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
                                    onGreenButtonClick(
                                            time = dateState,
                                            strTime = it.substringAfterLast('T'),
                                            onNextButtonClicked = onNextButtonClicked
                                    )
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

        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(
                    modifier = Modifier
                            .fillMaxWidth(),
                    onClick = { onNextButtonClicked(selected) }
            ) {
                Text(
                        text = stringResource(R.string.back)
                )
            }
            Button(
                    modifier = Modifier
                            .fillMaxWidth(),
                    onClick = { onNextButtonClicked(selected) }
            ) {
                Text(
                        text = stringResource(R.string.save)
                )
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
    strTime: String,
    onNextButtonClicked: (List<LocalDateTime>) -> Unit
) {
    val start = LocalDateTime.of(
            time.year,
            time.month,
            time.dayOfMonth,
            strTime.substringBefore(':').toInt(),
            strTime.substringAfterLast(':').toInt(),
            0
    )
    val end = start.plusMinutes(15)
    onNextButtonClicked(listOf(start, end))
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
            onNextButtonClicked = {selected: List<LocalDateTime> -> Unit}
    )
}

