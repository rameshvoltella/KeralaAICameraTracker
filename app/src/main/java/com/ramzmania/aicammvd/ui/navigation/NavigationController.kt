package com.ramzmania.aicammvd.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ramzmania.aicammvd.ui.screens.home.OnboardingUI

@Composable
fun AppNavigationController(navController: NavHostController) {
    NavHost(navController, startDestination = "slider_activity") {
        composable("slider_activity") {
        //    MainActivityScreen(navController)
        }
        composable("main_activity") {
      //      MainActivity()
        }
//        composable("third_activity") {
//            ThirdActivityScreen()
//        }
    }
}