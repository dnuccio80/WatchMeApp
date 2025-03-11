package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.ListDataClass
import com.google.gson.annotations.SerializedName

data class ListsResponse(
    @SerializedName("results") val results: List<ListResult>
)

fun ListsResponse.toListDataClass() : List<ListDataClass> {
    return results.map {
        ListDataClass(
            description = it.description,
            favoriteCount = it.favoriteCount,
            id = it.id,
            itemCount = it.itemCount,
            language = it.language,
            listType = it.listType,
            name = it.name,
            posterPath = it.posterPath
        )
    }
}

data class ListResult(
    @SerializedName("description") val description:String,
    @SerializedName("favorite_count") val favoriteCount: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("item_count") val itemCount: Int,
    @SerializedName("iso_639_1") val language: String,
    @SerializedName("list_type") val listType: String,
    @SerializedName("name") val name: String,
    @SerializedName("poster_path") val posterPath: String,
)