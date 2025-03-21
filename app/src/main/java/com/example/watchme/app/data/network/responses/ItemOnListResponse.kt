package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.ItemOnListDataClass
import com.google.gson.annotations.SerializedName

data class ItemOnListResponse (
    @SerializedName("id") val idList:Int,
    @SerializedName("item_present") val itemPresent: Boolean,
)

fun ItemOnListResponse.toItemOnListDataClass(): ItemOnListDataClass {
    return ItemOnListDataClass(
        idList = idList,
        itemPresent = itemPresent
    )
}
