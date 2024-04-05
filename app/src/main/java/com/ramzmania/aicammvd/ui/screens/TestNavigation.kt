package com.ramzmania.aicammvd.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramzmania.aicammvd.ui.navigation.Screens

@Composable
fun TestNavigationExample() {
    val navController = rememberNavController()
    val viewModel: TestViewModel = viewModel()


    val incrementCountData: (Int) -> Unit = viewModel::incrementCount
    val count by viewModel.count.collectAsState()

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
            Screenone(incrementCountData,
                navigateTo = { route ->
                    navController.navigate(route)
                }
            )
        }
        composable(route = Screens.Screentwo.route) {
            ScreenTwo(count
            ) { route ->
                navController.navigate(route)
            }
        }
    }
}