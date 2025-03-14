package com.example.watchme.app.data.network.responses.Dtos

import com.google.gson.annotations.SerializedName

data class DeleteItemFromListDto(
    @SerializedName("media_id") val mediaId: Int
)