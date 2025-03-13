package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.RatedItemDataClass
import com.example.watchme.app.ui.dataClasses.RatingRequestDataClass
import com.example.watchme.app.ui.dataClasses.TotalRatedResultsDataClass
import com.google.gson.annotations.SerializedName

data class RatingRequestResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("status_message") val statusMessage: String,
)

fun RatingRequestResponse.toRatingRequestDataClass(): RatingRequestDataClass {
    return RatingRequestDataClass(
        success = success,
        statusMessage = statusMessage
    )
}

// Dto = data transfer object
data class RatingRequestDto(
    val value: Float
)

data class RatedResponse(
    @SerializedName("results") val results:List<Rated>,
    @SerializedName("total_results") val totalResults: Int
)

fun RatedResponse.toRatedItemDataClass(): List<RatedItemDataClass> {
    return results.map {
        RatedItemDataClass(
            id = it.id,
            posterPath = it.posterPath,
            rating = it.rating
        )
    }
}

fun RatedResponse.toTotalResults(): TotalRatedResultsDataClass {
    return TotalRatedResultsDataClass(
        totalResults = totalResults
    )
}

data class Rated(
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("rating") val rating: Float,
)

data class RatedEpisodeResponse(
    @SerializedName("results") val results:List<RatedEpisode>,
    @SerializedName("total_results") val totalResults: Int
)

data class RatedEpisode(
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("rating") val rating: Float,
    @SerializedName("show_id") val showId: Int,
    @SerializedName("episode_number") val episodeNumber: Int,
    @SerializedName("season_number") val seasonNumber: Int,
)



