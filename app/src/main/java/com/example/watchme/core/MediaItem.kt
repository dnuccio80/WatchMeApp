package com.example.watchme.core

import android.media.browse.MediaBrowser

data class MediaItem(
    val id: Int,
    val title: String,
    val voteAverage: Float,
    val category: Categories,
    val seasonNumber: Int = 1,
    val episodeNumber: Int = 1,
)
