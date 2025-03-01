package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.CollectionSearchDataClass
import com.google.gson.annotations.SerializedName

data class CollectionSearchResponse(
    @SerializedName("results") val results: List<CollectionSearch>
)

fun CollectionSearchResponse.toCollectionSearchDataClass(): List<CollectionSearchDataClass> {
    return results.map {
        CollectionSearchDataClass(
            id = it.id,
            name = it.name,
            posterPath = it.posterPath
        )
    }
}

data class CollectionSearch(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String,
    @SerializedName("poster_path") val posterPath:String,
)

data class MovieSearchResponse(
    @SerializedName("results") val results: List<MovieSearch>
)

data class MovieSearch(
    @SerializedName("id") val id:Int,
    @SerializedName("title") val title:String,
    @SerializedName("poster_path") val posterPath:String,
)

data class SeriesSearchResponse(
    @SerializedName("results") val results: List<SeriesSearch>

)

data class SeriesSearch(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String,
    @SerializedName("poster_path") val posterPath:String,
)

data class PersonSearchResponse(
    @SerializedName("results") val results: List<PersonSearch>
)

data class PersonSearch(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String,
    @SerializedName("profile_path") val profilePath:String,
)