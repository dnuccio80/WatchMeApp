package com.example.watchme.app.ui.dataClasses

import com.example.watchme.app.data.network.responses.ItemListDetail

data class ListDetailsDataClass (
    val createdBy:String,
    val description:String,
    val favoriteCount:Int,
    val id:Int,
    val itemCount:Int,
    val items:List<ItemsListDetailDataClass>?,
    val name:String,
    val posterPath:String?,
)

data class ItemsListDetailDataClass(
    val posterPath: String?,
    val id: Int,
    val title: String?,
    val mediaType: String,
)