package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.RequestResponseDataClass
import com.google.gson.annotations.SerializedName

data class RequestResponse (
    @SerializedName("success") val success:Boolean,
    @SerializedName("status_message") val statusMessage:String
)

fun RequestResponse.toRequestResponseDataClass() : RequestResponseDataClass {
    return RequestResponseDataClass(
            success = success,
            statusMessage = statusMessage
        )
}

data class WatchListRequestDto(
    @SerializedName("media_type") val mediaType:String,
    @SerializedName("media_id") val mediaId:Int,
    @SerializedName("watchlist") val watchlist:Boolean,
)