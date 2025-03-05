package com.example.watchme.core

import com.example.watchme.R


sealed class Categories(val title: String, val icon: Int) {
    data object Collections : Categories("Collections", R.drawable.ic_collections)
    data object TvSeries : Categories("Tv Series", R.drawable.ic_tv)
    data object Movies : Categories("Movies", R.drawable.ic_movie)
    data object People : Categories("People", R.drawable.ic_star)
    data object TvEpisodes : Categories("Tv Episodes", R.drawable.ic_star)
}
