package com.example.ece_452_project.ui

import com.example.ece_452_project.data.DummyPlace
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.supportFragmentManager
import androidx.compose.runtime.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import android.view.View
import androidx.compose.ui.viewinterop.AndroidView
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
/**
 * Composable for the initial location selection screen
 */
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    locations: List<DummyPlace>
) {

    // not sure if this is right. I also tried just 'locationFragment().LocationScreen()'
//    val locationFragment = remember { LocationFragment() }
//    locationFragment.LocationScreen()


        FragmentContainer(
            modifier = Modifier.fillMaxSize(),
            fragmentManager = getFragmentManager,
            commit = { add(it, locationScreen()) }
        )
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