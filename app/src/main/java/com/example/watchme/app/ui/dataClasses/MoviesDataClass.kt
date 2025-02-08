package com.example.watchme.app.ui.dataClasses

import com.example.watchme.app.data.network.responses.Movie
import com.google.gson.annotations.SerializedName

data class PopularMoviesDataClass (
    val results: List<MovieDataClass>
)

data class MovieDataClass(
    val title: String,
    val overview: String,
    val poster: String,
    val voteAverage: Float,
    val voteCount: Int
)