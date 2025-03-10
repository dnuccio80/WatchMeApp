package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.FavoriteDataClass
import com.google.gson.annotations.SerializedName

data class FavoriteResponse (
    @SerializedName("success") val success:Boolean,
    @SerializedName("status_message") val statusMessage:String,
)

fun FavoriteResponse.toFavoriteDataClass() : FavoriteDataClass {
    return FavoriteDataClass(
        success = success,
        statusMessage = statusMessage
    )
}

// Dto = data transfer object
data class FavoriteRequestDto(
    @SerializedName("media_type") val mediaType:String,
    @SerializedName("media_id") val mediaId:Int,
    val favorite:Boolean
)