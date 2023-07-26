package com.example.ece_452_project.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.lifecycle.viewmodel.compose.viewModel
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorder
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@Composable
fun ReorderableList(
    modifier: Modifier = Modifier,
    vm: ReorderableListViewModel = viewModel(),
    newItems: List<String> = listOf(),
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
            Text(
                text = listTitle,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        }
        items(vm.items, { it }) { item ->
            ReorderableItem(state, item) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 8.dp else 0.dp)
                Column(
                    modifier = Modifier
                        .shadow(elevation.value)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)

                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            Icons.Default.List,
                            "",
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
                            modifier = Modifier.detectReorder(state)
                        )
//                        Image(
//                            painter = rememberAsyncImagePainter(item),
//                            contentDescription = null,
//                            modifier = Modifier.size(128.dp)
//                        )
                        Text(
                            text = item,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    Divider()
                }
            }
        }

    }

}

//@Preview(showBackground = true)
//@Composable
//fun ReorderableListPreview(){
//    ReorderableList(
////        modifier = Modifier
////            .fillMaxSize()
////            .padding(16.dp),
////        vm = viewModel(),
////        newItems = List(3) {
////            "String1";
////            "String2"
////            "String3"
////        }
//        newItems = List(3) { "https://picsum.photos/seed/compose$it/200/300" }
//    )
//}