package com.example.watchme.core

import com.example.watchme.app.data.network.responses.Series

sealed class SeriesOptions(val item:String){
    data object Episodes: SeriesOptions("episodes")
    data object Suggested: SeriesOptions("suggested")
    data object Details: SeriesOptions("details")
    data object Media: SeriesOptions("media")
    data object Credits: SeriesOptions("credits")
}
