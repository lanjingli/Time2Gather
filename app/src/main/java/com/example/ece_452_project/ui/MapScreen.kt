package com.example.ece_452_project.ui

import com.example.ece_452_project.data.DummyPlace
import androidx.compose.runtime.Composable
import android.os.Bundle
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentManager
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.fragment.app.FragmentTransaction
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.ece_452_project.R
import androidx.fragment.app.replace
import com.example.ece_452_project.databinding.LocationFragmentLayoutBinding
/**
 * Composable for the initial location selection screen
 */
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    fragmentManager: FragmentManager
) {

}
@Composable
fun FragmentContainer(
    modifier: Modifier = Modifier,
    fragmentManager: FragmentManager,
    commit: FragmentTransaction.(containerId: Int) -> Unit
) {
    val containerId by rememberSaveable { mutableStateOf(View.generateViewId()) }
    var initialized by rememberSaveable { mutableStateOf(false) }
    AndroidView(
        modifier = modifier,
        factory = { context ->
            FragmentContainerView(context)
                .apply { id = containerId }
        },
        update = { view ->
            if (!initialized) {
                fragmentManager.commit { commit(view.id) }
                initialized = true
            } else {
                fragmentManager.onContainerAvailable(view)
            }
        }
    )
}


/** Access to package-private method in FragmentManager through reflection */
private fun FragmentManager.onContainerAvailable(view: FragmentContainerView) {
    val method = FragmentManager::class.java.getDeclaredMethod(
        "onContainerAvailable",
        FragmentContainerView::class.java
    )
    method.isAccessible = true
    method.invoke(this, view)
}