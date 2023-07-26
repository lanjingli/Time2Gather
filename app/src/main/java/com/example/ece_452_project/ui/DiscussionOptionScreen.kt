package com.example.ece_452_project.ui

import androidx.compose.material.icons.filled.DateRange
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ece_452_project.R
import com.example.ece_452_project.data.Discussion
import com.example.ece_452_project.data.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscussionOptionScreen(
    modifier: Modifier = Modifier,
    user: User = User(),
//    event: Event = Event(),
    discussion: Discussion = Discussion(),
    onTimeButtonClicked: () -> Unit,
    onPlaceButtonClicked: () -> Unit,
    onFinishButtonClicked: (List<String>) -> Unit,
){
    val current = LocalDateTime.now()
    val compDateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    val compDate1 = compDateFormat.format(current)
    val compDate2 = compDateFormat.format(discussion.deadline)
    val cmp = compDate1.compareTo(compDate2)
    val gson = Gson()
    var vm : ReorderableListViewModel = viewModel()

    var butEnabled = false
    var butBorderColor = Color(0xFFBDBDBD)
    // Date has not expired
    if (cmp < 0) {
        butBorderColor = MaterialTheme.colorScheme.primary
        butEnabled = true
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
//        Spacer(modifier = Modifier.height(104.dp))
//        Card(
//            modifier = Modifier
//                .wrapContentHeight()
//                .fillMaxWidth(),
//            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
//        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = discussion.name,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = discussion.description,
                style = MaterialTheme.typography.titleMedium
            )
//        }
        Text(
            modifier = Modifier.padding(4.dp),
            text = "Deadline for Final Decisions is: $compDate2",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            //modifier = Modifier.fillMaxWidth(),
            onClick = { }
        ) {
            Text(
                text = "Suggest Option",
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            enabled = !butEnabled,
            enabled = true,
            //modifier = Modifier.fillMaxWidth(),
            onClick = {onFinishButtonClicked(vm.items)}
        ) {
            Text(
                text = "Save Ranking",
                fontSize = 16.sp
            )
        }

        ReorderableList(
            Modifier.weight(weight=1f),//, fill=false),
            vm = vm,
            newItems = discussion.options.map { gson.toJson(it)}
            //listTitle = "Rank Locations"
        )}
        //Spacer(modifier = Modifier.height(16.dp))

    }


@Preview(showBackground = true)
@Composable
fun EventOptionPreview(){
    DiscussionOptionScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        onTimeButtonClicked = {},
        onPlaceButtonClicked = {},
        onFinishButtonClicked = {items: List<String> -> Unit }
    )
}