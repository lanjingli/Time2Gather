package com.example.ece_452_project.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.material3.*
/**
 * Composable for the time selection screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuggestedPlacesScreen(
    modifier: Modifier = Modifier
) {
    val itemList = remember { mutableStateListOf<String>() }
    var newItemText by remember { mutableStateOf("") }

//    Scaffold(topBar = {
//        TopAppBar(title = { Text(text = "List") })
//    }) {padding ->
        Column(modifier = Modifier.padding(6.dp)) {
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                TextField(
                    value = newItemText,
                    onValueChange = { newItemText = it },
                    label = { Text(text = "New Item") },
                    modifier = Modifier.weight(1f)
                )
                //Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (newItemText.isNotBlank()) {
                            itemList.add(newItemText)
                            newItemText = ""
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Add")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                itemsIndexed(itemList.toList()) { index, item ->
                    ListItem(item = item) {
                        itemList.remove(item)
                    }
                }
            }
        }
    }
//}


@Composable
fun ListItem(item: String, onDelete: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = item, modifier = Modifier.weight(1f))
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuggestedPlacesPreview(){
    SuggestedPlacesScreen()
}
