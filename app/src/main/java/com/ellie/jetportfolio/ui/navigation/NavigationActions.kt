package com.ellie.jetportfolio.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.outlined.Face2
import androidx.compose.material.icons.twotone.Face2
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.ellie.jetportfolio.R
import com.ellie.jetportfolio.utils.navigateTo
import com.ellie.jetportfolio.utils.navigateToTopLevel


private object Route {
    const val SAMPLE = "Sample"
    const val PROFILE = "Profile"
    const val BUSINESS_CARD = "BusinessCard"
}

sealed class Screen(
    val route: String,
    @StringRes val titleId: Int,
    val onIcon: ImageVector? = null,
    val offIcon: ImageVector? = null,
) {
    fun getIcon(selected: Boolean): ImageVector? {
        return if (selected) onIcon else offIcon
    }

    object Sample : Screen(
        route = Route.SAMPLE,
        titleId = R.string.sample,
        onIcon = Icons.Default.Help,
        offIcon = Icons.Default.HelpOutline,
    )

    object Profile : Screen(
        route = Route.PROFILE,
        titleId = R.string.profile,
        onIcon = Icons.TwoTone.Face2,
        offIcon = Icons.Outlined.Face2,
    )

    object BusinessCard : Screen(
        route = Route.BUSINESS_CARD,
        titleId = R.string.business_card,
    ) {
        const val arg = "argBusinessCard"
        val argRoute = "$route/{$arg}"
    }
}

class NavigationActions(private val navController: NavHostController) {
    fun navigateToTopLevel(destination: Screen) {
        navController.navigateToTopLevel(destination.route)
    }

    fun navigateTo(destination: Screen) {
        navController.navigateTo(destination.route)
    }
}

// TODO: TBC of bottom / drawer menu
val TOP_LEVEL_DESTINATIONS = listOf(
    Screen.Profile,
    Screen.Sample,
)