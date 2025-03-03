package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.SearchDataClass
import com.google.gson.annotations.SerializedName

data class CollectionSearchResponse(
    @SerializedName("results") val results: List<CollectionSearch>
)

fun CollectionSearchResponse.toSearchDataClass(): List<SearchDataClass> {
    return results.map {
        SearchDataClass(
            id = it.id,
            name = it.name,
            posterPath = it.posterPath
        )
    }
}

data class CollectionSearch(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("poster_path") val posterPath: String,
)

data class MovieSearchResponse(
    @SerializedName("results") val results: List<MovieSearch>
)

fun MovieSearchResponse.toSearchDataClass(): List<SearchDataClass> {
    return results.map {
        SearchDataClass(
            id = it.id,
            name = it.title,
            posterPath = it.posterPath
        )
    }
}

data class MovieSearch(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String,
)

data class SeriesSearchResponse(
    @SerializedName("results") val results: List<SeriesSearch>
)

fun SeriesSearchResponse.toSearchDataClass(): List<SearchDataClass> {
    return results.map {
        SearchDataClass(
            id = it.id,
            name = it.name,
            posterPath = it.posterPath
        )
    }
}

data class SeriesSearch(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("poster_path") val posterPath: String,
)

data class PersonSearchResponse(
    @SerializedName("results") val results: List<PersonSearch>
)

fun PersonSearchResponse.toSearchDataClass(): List<SearchDataClass> {
    return results.map {
        SearchDataClass(
            id = it.id,
            name = it.name,
            posterPath = it.profilePath
        )
    }
}

data class PersonSearch(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("profile_path") val profilePath: String,
)