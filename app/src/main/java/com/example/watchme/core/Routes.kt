package com.example.watchme.core

sealed class Routes(val route: String) {
    data object Home: Routes("home")
    data object MovieDetails: Routes("movie_details")
}