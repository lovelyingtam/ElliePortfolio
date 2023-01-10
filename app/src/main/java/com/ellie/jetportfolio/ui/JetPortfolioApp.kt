package com.ellie.jetportfolio.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ellie.jetportfolio.data.model.fromJsonToBusinessCard
import com.ellie.jetportfolio.ui._sample.SampleScreen
import com.ellie.jetportfolio.ui.businesscard.BusinessCardScreen
import com.ellie.jetportfolio.ui.navigation.NavigationActions
import com.ellie.jetportfolio.ui.navigation.Screen
import com.ellie.jetportfolio.ui.navigation.TOP_LEVEL_DESTINATIONS
import com.ellie.jetportfolio.ui.profile.ProfileScreen
import com.ellie.jetportfolio.ui.profile.ProfileViewModel
import com.ellie.jetportfolio.utils.navigateTo
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetPortfolioApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }

    // TODO: Handle back button on root page?
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: Screen.Profile.route

    Scaffold(modifier = modifier, bottomBar = {
        BottomNavigationBar(
            selectedRoute = selectedDestination,
            navigateToTopLevel = navigationActions::navigateToTopLevel,
        )
    }) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = Screen.Profile.route,
        ) {
            // Sample Screen
            composable(route = Screen.Sample.route) {
                SampleScreen()
            }

            // Profile Screen
            composable(route = Screen.Profile.route) { backStackEntry ->
                Profile(navController, backStackEntry)
            }

            // Business Card Screen
            composable(
                route = Screen.BusinessCard.argRoute,
                arguments = listOf(navArgument(Screen.BusinessCard.arg) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                BusinessCard(backStackEntry)
            }
        }
    }
}

@Composable
fun Profile(
    navController: NavHostController, backStackEntry: NavBackStackEntry
) {
    val parentEntry = remember(backStackEntry) {
        navController.getBackStackEntry(Screen.Profile.route)
    }
    // Creates a ViewModel from the current BackStackEntry
    // Available in the androidx.hilt:hilt-navigation-compose artifact
    val viewModel = hiltViewModel<ProfileViewModel>(parentEntry)
    ProfileScreen(
        viewModel = viewModel,
        navToBusinessCard = { businessCard ->
            navController.navigateTo(
                Screen.BusinessCard.argRoute.replace(
                    "{${Screen.BusinessCard.arg}}", businessCard.toString()
                )
            )
        },
    )
}

@Composable
fun BusinessCard(backStackEntry: NavBackStackEntry) {
    val json = backStackEntry.arguments?.getString(Screen.BusinessCard.arg)!!
    Timber.d("JSON: $json")
    val bc = json.fromJsonToBusinessCard()
    Timber.d("bc: $bc")
    BusinessCardScreen(businessCard = bc)
}

@Composable
fun BottomNavigationBar(
    selectedRoute: String,
    navigateToTopLevel: (Screen) -> Unit,
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        TOP_LEVEL_DESTINATIONS.forEach { screen ->
            NavigationBarItem(selected = selectedRoute == screen.route, onClick = {
                navigateToTopLevel(screen)
            }, icon = {
                screen.getIcon(selectedRoute == screen.route)?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = stringResource(id = screen.titleId),
                    )
                }
            }, label = {
                Text(
                    text = stringResource(id = screen.titleId),
                    style = MaterialTheme.typography.labelMedium,
                )
            })
        }
    }
}