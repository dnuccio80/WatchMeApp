package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.RatingDataClass
import com.google.gson.annotations.SerializedName

data class RatingResponse(
    @SerializedName("success") val success:Boolean,
    @SerializedName("status_message") val statusMessage:String,
)

fun RatingResponse.toRatingDataClass(): RatingDataClass {
    return RatingDataClass(
        success = success,
        statusMessage = statusMessage
    )
}

// Dto = data transfer object
data class RatingRequestDto(
    val value:Float
)

//data class RatedMovieReponse(
//    @SerializedName("id")
//)
