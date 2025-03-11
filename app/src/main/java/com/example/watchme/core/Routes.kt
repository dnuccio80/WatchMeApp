package com.example.watchme.core

sealed class Routes(val route: String) {
    data object Home: Routes("home")

    data object MovieDetails: Routes("detailsMovie/{movieId}"){
        fun createRoute(movieId: Int) = "detailsMovie/$movieId"
    }

    data object CollectionDetails: Routes("collectionDetails/{collectionId}"){
        fun createRoute(collectionId:Int) = "collectionDetails/$collectionId"
    }

    data object SeriesDetails: Routes("seriesDetails/{seriesId}"){
        fun createRoute(seriesId: Int) = "seriesDetails/$seriesId"
    }

    data object PeopleDetails: Routes("peopleDetails/{personId}"){
        fun createRoute(personId:Int) = "peopleDetails/$personId"
    }

    data object EpisodeDetails: Routes("episodeDetails/{seriesId}/{episodeId}/{seasonNumber}"){
        fun createRoute(seriesId:Int, episodeId:Int, seasonNumber:Int) = "episodeDetails/$seriesId/$episodeId/$seasonNumber"
    }

    data object Search: Routes("search")

    data object Profile: Routes("profile")

    data object Ratings: Routes("ratings")


}