package com.ellie.jetportfolio.utils

import androidx.navigation.NavHostController
import timber.log.Timber

// Pop up to section root
fun NavHostController.navigateToTopLevel(route: String) = this.navigate(route) {
    // Pop up to the start destination of the graph to
    // avoid building up a large stack of destinations
    // on the back stack as users select items
    Timber.d("navigateToTopLevel=${route}")
    if (currentBackStackEntry?.destination?.route != route) {
        popUpTo(graph.id)
    }

    // Avoid multiple copies of the same destination when
    // re-selecting the same item
    launchSingleTop = true
    // Restore state when re-selecting a previously selected item
    restoreState = true
}

fun NavHostController.navigateTo(route: String) = this.navigate(route) {
    // Avoid multiple copies of the same destination when
    // re-selecting the same item
    launchSingleTop = true
}
