package com.example.watchme.core

data class RatedItem(
    val id: Int,
    val posterPath: String,
    val episodeNumber: Int = 0,
    val seasonNumber: Int = 0,
)