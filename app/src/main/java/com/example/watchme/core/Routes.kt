package com.example.watchme.core

sealed class Routes(val route: String) {
    data object Home: Routes("home")
    data object MovieDetails: Routes("detailsMovie/{movieId}"){
        fun createRoute(movieId: Int) = "detailsMovie/$movieId"
    }
    data object SeriesDetails: Routes("seriesDetails/{seriesId}"){
        fun createRoute(seriesId: Int) = "seriesDetails/$seriesId"
    }
    data object PeopleDetails: Routes("peopleDetails/{personId}"){
        fun createRoute(personId:Int) = "peopleDetails/$personId"
    }
}