package com.ramzmania.aicammvd.ui.navigation

sealed class Screens(val route : String) {
    object LoadingScreen : Screens("loading")
    object LoginScreen : Screens("home")


    object Screenone : Screens("scr1")
    object Screentwo : Screens("scr2")
    object Screenthree : Screens("scr3")


}