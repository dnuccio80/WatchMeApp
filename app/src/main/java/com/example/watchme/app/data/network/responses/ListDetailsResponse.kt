package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.ItemsListDetailDataClass
import com.example.watchme.app.ui.dataClasses.ListDetailsDataClass
import com.google.gson.annotations.SerializedName

data class ListDetailsResponse(
    @SerializedName("created_by") val createdBy: String,
    @SerializedName("description") val description: String,
    @SerializedName("favorite_count") val favoriteCount: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("item_count") val itemCount: Int,
    @SerializedName("items") val items: List<ItemListDetail>,
    @SerializedName("name") val name: String,
    @SerializedName("poster_path") val posterPath: String,
)

fun ListDetailsResponse.toListDetailsDataClass(): ListDetailsDataClass {
    return ListDetailsDataClass(
        createdBy = createdBy,
        description = description,
        favoriteCount = favoriteCount,
        id = id,
        itemCount = itemCount,
        items = items.map { it.toItemsListDetailDataClass() },
        name = name,
        posterPath = posterPath
    )
}

data class ItemListDetail(
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("media_type") val mediaType: String,
)

fun ItemListDetail.toItemsListDetailDataClass(): ItemsListDetailDataClass {
    return ItemsListDetailDataClass(
        posterPath = posterPath,
        id = id,
        title = title,
        mediaType = mediaType
    )
}