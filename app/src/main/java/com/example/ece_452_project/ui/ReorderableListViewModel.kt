package com.example.ece_452_project.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.burnoutcrew.reorderable.ItemPosition

class ReorderableListViewModel: ViewModel() {
    var items by mutableStateOf(listOf<String>())

    fun updateItems(newItems: List<String>) {
        items = newItems
    }
    fun onMove(from: ItemPosition, to: ItemPosition) {
        items = items.toMutableList().apply {
            add(items.indexOfFirst { it == to.key }, removeAt(items.indexOfFirst { it == from.key }))
        }
    }

    fun canDragOver(draggedOver: ItemPosition, dragging: ItemPosition) = items.any { it == draggedOver.key }
}