package com.example.watchme.app.ui.dataClasses


data class CollectionDetailsDataClass(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val parts: List<MovieDataClass>,
)
