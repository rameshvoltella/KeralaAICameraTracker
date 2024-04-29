/**
 * HomeNavigation: A composable function responsible for setting up the navigation within the home screen.
 * It uses Jetpack Navigation Compose to define the navigation graph and handle navigation events.
 * This composable function serves as the entry point for the home screen navigation.
 */
package com.ramzmania.aicammvd.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ramzmania.aicammvd.ui.component.home.HomeLayer

@Composable
fun HomeNavigation() {
    // Create a NavHost with a NavController
    val navController = rememberNavController()

    // Retrieve the ViewModelStoreOwner from composition local
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    // Define the navigation graph within the NavHost
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ) {
        // Define a composable for the home screen
        composable(route = Screens.HomeScreen.route) {
            // Provide the ViewModelStoreOwner via CompositionLocalProvider
            CompositionLocalProvider(
                LocalViewModelStoreOwner provides viewModelStoreOwner
            ) {

                // Render the HomeLayer composable, passing the ViewModelStoreOwner and navigation actions
                HomeLayer(viewModelStoreOwner,
                    navigateTo = { route ->
                        navController.navigate(route)
                    }
                )
            }
        }

    }
}