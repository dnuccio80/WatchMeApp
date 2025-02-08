package com.example.watchme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchme.app.data.network.responses.MovieResponse
import com.example.watchme.app.domain.GetNowPlayingMoviesUseCase
import com.example.watchme.app.domain.GetPopularMoviesUseCase
import com.example.watchme.app.domain.GetTopRatedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    getPopularMoviesUseCase: GetPopularMoviesUseCase,
    getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
) : ViewModel() {

    private val _popularMovies: MutableStateFlow<MovieResponse> = MutableStateFlow(MovieResponse(emptyList()))
    val popularMovies = _popularMovies

    private val _nowPlayingMovies: MutableStateFlow<MovieResponse> = MutableStateFlow(MovieResponse(emptyList()))
    val nowPlayingMovies = _nowPlayingMovies

    private val _topRatedMovies: MutableStateFlow<MovieResponse> = MutableStateFlow(MovieResponse(emptyList()))
    val topRatedMovies = _topRatedMovies

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _popularMovies.value = getPopularMoviesUseCase()
            _nowPlayingMovies.value = getNowPlayingMoviesUseCase()
            _topRatedMovies.value = getTopRatedMoviesUseCase()
        }
    }
}