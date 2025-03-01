package com.example.watchme.core

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.watchme.R


sealed class SearchTypes(val title: String, val icon: Int) {
    data object Collections : SearchTypes("Collections", R.drawable.ic_collections)
    data object TvSeries : SearchTypes("Tv Series", R.drawable.ic_tv)
    data object Movies : SearchTypes("Movies", R.drawable.ic_movie)
    data object People : SearchTypes("People", R.drawable.ic_star)
}
