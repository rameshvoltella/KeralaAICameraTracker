package com.ramzmania.aicammvd.ui.navigation

sealed class Screens(val route : String) {
    object LoadingScreen : Screens("loading")
    object LoginScreen : Screens("home")
}