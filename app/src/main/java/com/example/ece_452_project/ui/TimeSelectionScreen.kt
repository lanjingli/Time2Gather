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
    onNextButtonClicked: (List<LocalDateTime>) -> Unit
){
    var selected by rememberSaveable { mutableStateOf(List<LocalDateTime>(2){LocalDateTime.now()})}

    Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
    ){
        var dateState by remember {
            mutableStateOf(LocalDateTime.now())
        }
        val simpleDateFormat = DateTimeFormatter.ofPattern("EE MMM dd, yyyy")
        Text(
                text = stringResource(R.string.select_time),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineMedium
        )
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
                            .background(color = Color(0xFF6750A4))
            ) {
                Button(
                        modifier = Modifier.fillMaxHeight(),
                        onClick = {
                            dateState = dateState.plusDays(-1)
                        }) {
                    Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                    )
                }
                Text(
                        text = "${simpleDateFormat.format(dateState).replaceRange(8, 9, "")}",
                        fontSize = 24.sp,
                        color = Color.White
                )
                Button(
                        modifier = Modifier.fillMaxHeight(),
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

        val numCols = minOf(friends.size + 1, 5)
        //val lazyGridState = rememberLazyGridState()
        val itemsList = (0 until numCols * 97).toList()
        val itemModifier = Modifier
                //.border(1.dp, Color(0xFF6750A4))
                .border(
                        BorderStroke(
                                width = Dp.Hairline,
                                color = Color.White
                        )
                )
                .background(color = Color(0xFF6750A4))
                .fillMaxWidth()
                .wrapContentSize()
        LazyVerticalGrid(
                columns = GridCells.Fixed(numCols),
                modifier = Modifier.fillMaxSize()
        ) {
            items(itemsList) {
                if (it < numCols) {
                    Box(modifier = itemModifier) {
                        Text(
                            color = Color.White,
                            text = when(it) {
                                0 -> {"Time"}
                                1 -> {currentUser.name}
                                else -> {
                                    friends[it - 2].name
                                }
                            }
                        )
                    }
                } else {
                    if (it % numCols == 0) {
                        val dailyCalendarFormat = DateTimeFormatter.ofPattern("HH:mm")
                        var dailyCalendarTime = LocalDateTime.of(dateState.year, dateState.month, dateState.dayOfMonth, 0, 0, 0)
                        Box(modifier = itemModifier) {
                            Text(
                                color = Color.White,
                                text = dailyCalendarFormat
                                    .format(dailyCalendarTime
                                        .plusMinutes(
                                            (
                                                (it / numCols - 1) * 15
                                            ).toLong()
                                        )
                                    )
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

@Preview(showBackground = true)
@Composable
fun TimeSelectionPreview(){
    TimeSelectionScreen(
            currentUser = DummyData.users.first { it.username == "alpha" },
            modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            friends = DummyData.users.filter {it.username != "alpha"},
            onNextButtonClicked = {selected: List<LocalDateTime> -> Unit}
    )
}

