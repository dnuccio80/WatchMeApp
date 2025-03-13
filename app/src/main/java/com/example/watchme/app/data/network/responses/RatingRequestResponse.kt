package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.RatedItemDataClass
import com.example.watchme.app.ui.dataClasses.RatingRequestDataClass
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
    @SerializedName("results") val results:List<Rated>
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

data class Rated(
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("rating") val rating: Float,
)




