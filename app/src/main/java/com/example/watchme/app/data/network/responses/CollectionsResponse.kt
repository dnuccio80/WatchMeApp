package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.CollectionDataClass
import com.example.watchme.app.ui.dataClasses.CollectionDetailsDataClass
import com.google.gson.annotations.SerializedName

data class CollectionResponse(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String,
    @SerializedName("overview") val overview:String,
    @SerializedName("poster_path") val posterPath:String,
    @SerializedName("backdrop_path") val backdropPath:String,
    @SerializedName("parts") val parts:List<Movie>,
)

fun CollectionResponse.toCollectionDataClass(): CollectionDetailsDataClass {
    return CollectionDetailsDataClass(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        parts = parts.map { it.toMovieDataClass() }
    )
}