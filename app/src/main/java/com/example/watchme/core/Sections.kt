package com.example.watchme.core

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.example.watchme.R

sealed class Sections(@StringRes val title:Int){
    data object Episodes: Sections( R.string.episodes)
    data object Suggested: Sections(R.string.suggested)
    data object Details: Sections(R.string.details)
    data object Media: Sections(R.string.media)
    data object Credits: Sections(R.string.credits)
    data object Biography: Sections(R.string.biography)
    data object Movies: Sections(R.string.movies)
    data object Series: Sections(R.string.tv_series)
    data object Watch: Sections(R.string.watch)
}
