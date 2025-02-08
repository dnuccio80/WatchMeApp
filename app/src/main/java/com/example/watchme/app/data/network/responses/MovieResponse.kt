package com.example.watchme.app.data.network.responses

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results") val result: List<Movie>
)

data class Movie(
    @SerializedName("id") val id:Int,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("title") val title: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val poster: String,
    @SerializedName("backdrop_path") val backdrop: String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int
)


