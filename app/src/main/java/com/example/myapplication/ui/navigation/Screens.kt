package com.example.myapplication.ui.navigation

sealed class Screens(val route: String){
    object MainScreen: Screens(route = "Main")
    object ResultScreen: Screens(route = "Result")
}
