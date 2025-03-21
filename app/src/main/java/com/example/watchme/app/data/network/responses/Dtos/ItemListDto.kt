package com.example.watchme.app.data.network.responses.Dtos

import com.google.gson.annotations.SerializedName

data class ItemListDto(
    @SerializedName("media_id") val mediaId: Int
)