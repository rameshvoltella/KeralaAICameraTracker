package com.ramzmania.aicammvd.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ramzmania.aicammvd.ui.component.home.HomeLayer

@Composable
fun HomeNavigation() {
    val navController = rememberNavController()
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ) {

        composable(route = Screens.HomeScreen.route) {
            HomeLayer(viewModelStoreOwner,
                navigateTo = { route ->
                    navController.navigate(route)
                }
            )
        }

    }
}