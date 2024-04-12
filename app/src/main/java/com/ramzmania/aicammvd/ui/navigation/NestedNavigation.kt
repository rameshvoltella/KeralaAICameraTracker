package com.ramzmania.aicammvd.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.ramzmania.aicammvd.viewmodel.home.HomeViewModel
import androidx.compose.runtime.collectAsState
@Composable
fun NestedNavigationExample(homeViewModel: HomeViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.LoadingScreen.route
    ) {
//        composable(route = Screens.LoadingScreen.route) {
//            InitialLoadingScreen(viewModel = homeViewModel) {
//                navController.navigate(it,)
//            }
//        }
        composable(route = Screens.LoadingScreen.route) {
//            InitialLoadingScreen(
//                navigateTo = { route ->
//                    navController.navigate(route)
//                }
//            )
        }
        composable(route = Screens.LoginScreen.route) {
            //LoginScreen()
        }
    }
}