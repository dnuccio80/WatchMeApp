package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.CreateListDataClass
import com.google.gson.annotations.SerializedName

data class CreateListResponse(
    @SerializedName("success") val success:Boolean,
    @SerializedName("status_message") val statusMessage:String
)

fun CreateListResponse.toCreateListDataClass(): CreateListDataClass {
    return CreateListDataClass(
        success = success,
        statusMessage = statusMessage
    )
}

data class CreateListDto(
    @SerializedName("name") val name:String,
    @SerializedName("description") val description:String,
    @SerializedName("language") val language:String
)