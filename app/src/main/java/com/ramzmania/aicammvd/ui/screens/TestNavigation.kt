package com.ramzmania.aicammvd.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramzmania.aicammvd.ui.navigation.Screens

@Composable
fun TestNavigationExample() {
    val navController = rememberNavController()
    val viewModel: TestViewModel = viewModel()


    val incrementCountData: (Int) -> Unit = viewModel::incrementCount
    val incrementCountData2: () -> Unit = viewModel::fetchAiLocationInfo

    val count by viewModel.count.collectAsState()
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    NavHost(
        navController = navController,
        startDestination = Screens.Screenone.route
    ) {
//        composable(route = Screens.LoadingScreen.route) {
//            InitialLoadingScreen(viewModel = homeViewModel) {
//                navController.navigate(it,)
//            }
//        }
        composable(route = Screens.Screenone.route) {
            Screenone(viewModelStoreOwner
            ) { route ->
                navController.navigate(route)
            }
        }
        composable(route = Screens.Screentwo.route) {
            CompositionLocalProvider(
                LocalViewModelStoreOwner provides viewModelStoreOwner
            ) {
                ScreenTwo(count
                ) { route ->
                    navController.navigate(route)
                }
            }

        }
        composable(route = Screens.Screenthree.route) {
            CompositionLocalProvider(
                LocalViewModelStoreOwner provides viewModelStoreOwner
            ) {
                ScreenThree(count
                ) { route ->
                    navController.navigate(route)
                }
            }

        }
    }
}