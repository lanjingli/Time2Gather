package com.example.ece_452_project.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ece_452_project.data.TimePlace
import com.example.ece_452_project.data.User
import com.google.gson.Gson
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorder
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@Composable
fun ReorderableList(
    modifier: Modifier = Modifier,
    vm: ReorderableListViewModel = viewModel(),
    newItems: List<String> = emptyList(),
    listTitle: String = ""
) {

    LaunchedEffect(newItems) {
        vm.updateItems(newItems)
    }

    val state = rememberReorderableLazyListState(onMove = vm::onMove, canDragOver = vm::canDragOver)
    LazyColumn(
        state = state.listState,
        modifier = modifier
            .then(Modifier.reorderable(state))
    ) {
        item() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = listTitle,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        items(vm.items, { it }) {item ->
            ReorderableItem(state, item) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 8.dp else 0.dp)
                val obj = Gson().fromJson(item, TimePlace::class.java)
                Column(
                    modifier = Modifier
                        .shadow(elevation.value)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)

                ) {
                    Card() {
                        Row(
                            Modifier
                                .padding(vertical = 16.dp)
                                .detectReorder(state),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
//                            Image(
//                                Icons.Default.List,
//                                "",
//                                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
//                                //modifier = Modifier.detectReorder(state)
//                            )
                            Icon(
                                modifier = Modifier
                                    .size(36.dp)
                                    .weight(1f),
                                imageVector = Icons.Default.List,
                                contentDescription = ""
                            )
//                        Image(
//                            painter = rememberAsyncImagePainter(item),
//                            contentDescription = null,
//                            modifier = Modifier.size(128.dp)
//                        )
                            Text(
                                text = obj.location,
                                modifier = Modifier.padding(16.dp).weight(5f),
                                style = MaterialTheme.typography.bodyLarge
                            )
//                            Text(
//                                text = (index + 1).toString(),
//                            )
                        }
//                    Divider()
                    }
                }
            }
        }

    }

}