package com.ramzmania.aicammvd.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ramzmania.aicammvd.ui.component.home.BasicHomeLayer
import com.ramzmania.aicammvd.ui.component.home.InitialLoadingScreen
import com.ramzmania.aicammvd.ui.component.home.LoginScreen
import com.ramzmania.aicammvd.viewmodel.home.HomeViewModel
import androidx.compose.runtime.collectAsState
import com.ramzmania.aicammvd.ui.navigation.Screens

@Composable
fun TestNavigationExample(viewModel: TestViewModel) {
    val navController = rememberNavController()
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
            Screenone(testViewModel = viewModel,
                navigateTo = { route ->
                    navController.navigate(route)
                }
            )
        }
        composable(route = Screens.Screentwo.route) {
            ScreenTwo(testViewModel= viewModel
                ,navigateTo = { route ->
                navController.navigate(route)
            })
        }
    }
}