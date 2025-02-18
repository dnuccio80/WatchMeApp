package com.example.watchme.app.ui.dataClasses

data class SeriesDataClass(
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int>,
    val id: Int,
    val overview: String,
    val posterPath: String,
    val firstAirDate: String,
    val name: String,
    val voteAverage: Float,
    val voteCount: Int,
)