package com.ramzmania.aicammvd.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun HomeNavGraph(navController: NavHostController) {
//    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavGraph.INITIAL_SCREEN) {
//        composable(NavGraph.INITIAL_SCREEN) { InitialLoadingScreen() }
//        composable(NavGraph.HOME_SCREEN) { BasicHomeLayer(dataCameraList) }
    }
}


