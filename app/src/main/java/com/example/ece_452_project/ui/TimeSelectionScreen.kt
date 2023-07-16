package com.example.ece_452_project.ui

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.R
import com.example.ece_452_project.data.DummyData
import com.example.ece_452_project.data.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TimeSelectionScreen(
    users: List<User>,
    modifier: Modifier = Modifier,
    onNextButtonClicked: (List<LocalDateTime>) -> Unit
){
    var selected by rememberSaveable { mutableStateOf(List<LocalDateTime>(2){LocalDateTime.now()})}

    Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
    ){
        var dateState by remember {
            mutableStateOf(LocalDateTime.now())
        }
        val simpleDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        Text(
                text = stringResource(R.string.select_time),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineMedium
        )
        Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                dateState = dateState.plusDays(-1)
            }) {
                Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                )
            }
            Text(
                    text = "Date: ${simpleDateFormat.format(dateState)}",
                    fontSize = 20.sp
            )
            Button(onClick = {
                dateState = dateState.plusDays(1)
            }) {
                Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                )
            }
        }
        val numCols = 4
        //val lazyGridState = rememberLazyGridState()
        val itemsList = (1..100).toList()
        val itemModifier = Modifier
                .border(1.dp, Color.Blue)
                .fillMaxWidth()
                .wrapContentSize()
        LazyVerticalGrid(
                columns = GridCells.Fixed(numCols),
                modifier = Modifier.fillMaxSize()
        ) {
            items(itemsList) {
                Text("Item is $it", itemModifier)
            }
        }
        Button(
                modifier = Modifier
                        .fillMaxWidth(),
                onClick = { onNextButtonClicked(selected) }
        ){
            Text(
                    text = stringResource(R.string.next)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimeSelectionPreview(){
    TimeSelectionScreen(
            modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            users = DummyData.users,
            onNextButtonClicked = {selected: List<LocalDateTime> -> Unit}
    )
}

