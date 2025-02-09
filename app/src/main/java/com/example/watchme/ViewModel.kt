package com.example.watchme

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchme.app.data.network.responses.MovieResponse
import com.example.watchme.app.data.network.responses.ProvidersResponse
import com.example.watchme.app.domain.movies.GetNowPlayingMoviesUseCase
import com.example.watchme.app.domain.movies.GetPopularMoviesUseCase
import com.example.watchme.app.domain.movies.GetTopRatedMoviesUseCase
import com.example.watchme.app.domain.providers.GetProvidersUseCase
import com.example.watchme.ui.theme.IntermediateVoteColor
import com.example.watchme.ui.theme.NegativeVoteColor
import com.example.watchme.ui.theme.PositiveVoteColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    getPopularMoviesUseCase: GetPopularMoviesUseCase,
    getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    getProvidersUseCase: GetProvidersUseCase,
) : ViewModel() {

    private val _popularMovies: MutableStateFlow<MovieResponse> = MutableStateFlow(MovieResponse(emptyList()))
    val popularMovies = _popularMovies

    private val _nowPlayingMovies: MutableStateFlow<MovieResponse> = MutableStateFlow(MovieResponse(emptyList()))
    val nowPlayingMovies = _nowPlayingMovies

    private val _topRatedMovies: MutableStateFlow<MovieResponse> = MutableStateFlow(MovieResponse(emptyList()))
    val topRatedMovies = _topRatedMovies

    private val _providers: MutableStateFlow<ProvidersResponse> = MutableStateFlow(ProvidersResponse(emptyList()))
    val providers = _providers

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _popularMovies.value = getPopularMoviesUseCase()
            _nowPlayingMovies.value = getNowPlayingMoviesUseCase()
            _topRatedMovies.value = getTopRatedMoviesUseCase()
            _providers.value = getProvidersUseCase()
        }
    }

    fun getPercentageColor(voteAverage: Int): Color {
        return when (voteAverage) {
            in 0..40 -> NegativeVoteColor
            in 41..69 -> IntermediateVoteColor
            else -> PositiveVoteColor
        }
    }

}

