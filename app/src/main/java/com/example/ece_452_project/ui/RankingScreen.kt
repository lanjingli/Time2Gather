package com.example.ece_452_project.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ece_452_project.R
import com.example.ece_452_project.data.DummyData
import com.example.ece_452_project.data.User
import com.example.ece_452_project.generateAllEventsList
import com.example.ece_452_project.generateAllEventsNamesOnly
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable


//
//@Composable
//private fun VerticalReorderList(
//    modifier: Modifier = Modifier,
//    vm: RankingViewModel,
//) {
//    val state = rememberReorderableLazyListState(onMove = vm::moveDog)
//    LazyColumn(
//        state = state.listState,
//        modifier = modifier.reorderable(state)
//    ) {
//        items(vm.dogs, { item -> item.key }) { item ->
//            ReorderableItem(state, item.key) { dragging ->
//                val elevation = animateDpAsState(if (dragging) 8.dp else 0.dp)
//                Column(
//                    modifier = Modifier
//                        .detectReorderAfterLongPress(state)
//                        .shadow(elevation.value)
//                        .fillMaxWidth()
//                        .background(MaterialTheme.colorScheme.surface)
//                ) {
//                    Text(
//                        text = item.title,
//                        modifier = Modifier.padding(16.dp)
//                    )
//                    Divider()
//                }
//            }
//        }
//    }
//}


@Composable
fun VerticalReorderList(user: User = User()) {

    var data = remember { generateAllEventsNamesOnly(user) }
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        data = data.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    })
    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
    ) {
        items(data, { it }) { item ->
            ReorderableItem(state, key = item) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                Column(
                    modifier = Modifier
                        .shadow(elevation.value)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Text(item)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RankingScreenPreview(){
    VerticalReorderList(user = DummyData.users[0])
}