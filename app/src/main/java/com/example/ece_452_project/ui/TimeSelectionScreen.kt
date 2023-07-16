package com.example.ece_452_project.ui

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
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ece_452_project.R
import java.time.LocalDateTime

@Composable
fun TimeSelectionScreen(
    title: String,
    modifier: Modifier = Modifier,
    onNextButtonClicked: (List<LocalDateTime>) -> Unit
){
    var selected by rememberSaveable { mutableStateOf(List<LocalDateTime>(2){LocalDateTime.now()})}

    Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
    ){
        Text(
                modifier = Modifier.padding(16.dp),
                text = title,
                style = MaterialTheme.typography.headlineMedium
        )
        val numRows = 12
        //val lazyGridState = rememberLazyGridState()
        val itemsList = (1..100).toList()
        val itemModifier = Modifier.border(1.dp, Color.Blue).width(80.dp).wrapContentSize()
        LazyHorizontalGrid(
                rows = GridCells.Fixed(numRows),
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
            title = "Test",
            onNextButtonClicked = {selected: List<LocalDateTime> -> Unit}
    )
}

