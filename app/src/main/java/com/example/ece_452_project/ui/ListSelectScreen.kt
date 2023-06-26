package com.example.ece_452_project.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ece_452_project.R

@Composable
fun ListSelectScreen(
    title: String,
    options: List<String>,
    exclusive: Boolean = false,
    modifier: Modifier = Modifier,
    onNextButtonClicked: (List<Boolean>) -> Unit
){
    var selected by rememberSaveable { mutableStateOf(List<Boolean>(options.size) {false}) }

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
        options.forEachIndexed { i, item ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    if (exclusive) {
                        RadioButton(
                            selected = selected[i],
                            onClick = {
                                var tmp = MutableList<Boolean>(options.size) {false}
                                tmp[i] = true
                                selected = tmp.toList()
                            }
                        )
                    }
                    else {
                        Checkbox(
                            checked = selected[i],
                            onCheckedChange = {
                                var tmp = selected.toMutableList()
                                tmp[i] = it
                                selected = tmp
                            }
                        )
                    }
                    Text(item, modifier = Modifier.padding(end = 16.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
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
fun ListSelectPreview(){
    ListSelectScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        title = "Options",
        options = listOf<String>("Option 1", "Option 2", "Option 3"),
        onNextButtonClicked = {selected: List<Boolean> -> Unit}
    )
}