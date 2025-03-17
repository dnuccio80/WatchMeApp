package com.example.watchme.core

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.example.watchme.R


sealed class Categories(@StringRes val title: Int, val icon: Int, val mediaType:String = "null") {
    data object Collections : Categories(R.string.collections, R.drawable.ic_collections)
    data object TvSeries : Categories(R.string.tv_series, R.drawable.ic_tv, "tv")
    data object Movies : Categories(R.string.movies, R.drawable.ic_movie, "movie")
    data object People : Categories(R.string.people, R.drawable.ic_star)
    data object TvEpisodes : Categories(R.string.tv_episodes, R.drawable.ic_star)
}
