package com.example.watchme.app.ui.dataClasses

import com.example.watchme.app.data.network.responses.Movie
import com.google.gson.annotations.SerializedName

data class PopularMoviesDataClass (
    val results: List<MovieDataClass>
)

data class MovieDataClass(
    val id: Int,
    val genreIds: List<Int>,
    val releaseDate: String,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val voteAverage: Float,
    val voteCount: Int
)
