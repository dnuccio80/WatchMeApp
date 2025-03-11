package com.example.watchme.core

sealed class ShowListType (val type:String){
    data object Movies: ShowListType("Show Movies")
    data object Series: ShowListType("Show Series")
    data object All: ShowListType("Show All")
}