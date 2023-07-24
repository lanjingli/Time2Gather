package com.example.ece_452_project.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.burnoutcrew.reorderable.ItemPosition
import com.example.ece_452_project.data.User
import kotlinx.coroutines.flow.update


data class ItemData(val title: String, val key: String)

class RankingViewModel : ViewModel() {

    var dogs by mutableStateOf(List(500) {
        ItemData("Dog $it", "id$it")
    })


    fun moveDog(from: ItemPosition, to: ItemPosition) {
        dogs = dogs.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    }

}