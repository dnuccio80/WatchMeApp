package com.example.watchme.app.data.network.responses

import android.text.BoringLayout
import com.example.watchme.app.ui.dataClasses.WatchListDataClass
import com.google.gson.annotations.SerializedName

data class WatchListResponse (
    @SerializedName("success") val success:Boolean,
    @SerializedName("status_message") val statusMessage:String
)

fun WatchListResponse.toWatchListDataClass() : WatchListDataClass {
    return WatchListDataClass(
            success = success,
            statusMessage = statusMessage
        )
}

data class WatchListRequestDto(
    @SerializedName("media_type") val mediaType:String,
    @SerializedName("media_id") val mediaId:Int,
    @SerializedName("watchlist") val watchlist:Boolean,
)