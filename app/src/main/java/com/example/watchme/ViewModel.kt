package com.example.watchme

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.watchme.app.data.network.responses.DetailsMovieResponse
import com.example.watchme.app.data.network.responses.ImageBackdrop
import com.example.watchme.app.data.network.responses.MovieCreditsResponse
import com.example.watchme.app.data.network.responses.MovieResponse
import com.example.watchme.app.data.network.responses.ProvidersResponse
import com.example.watchme.app.domain.movies.GetImageListByIdUseCase
import com.example.watchme.app.domain.movies.GetMovieCreditsByIdUseCase
import com.example.watchme.app.domain.movies.GetMovieDetailsByIdUseCase
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    getPopularMoviesUseCase: GetPopularMoviesUseCase,
    getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    getProvidersUseCase: GetProvidersUseCase,
    private val getMovieDetailsByIdUseCase: GetMovieDetailsByIdUseCase,
    private val getMovieCreditsByIdUseCase: GetMovieCreditsByIdUseCase,
    private val getMovieImageListByIdUseCase: GetImageListByIdUseCase
) : ViewModel() {

    private val _popularMovies: MutableStateFlow<MovieResponse> =
        MutableStateFlow(MovieResponse(emptyList()))
    val popularMovies: StateFlow<MovieResponse> = _popularMovies

    private val _nowPlayingMovies: MutableStateFlow<MovieResponse> =
        MutableStateFlow(MovieResponse(emptyList()))
    val nowPlayingMovies: StateFlow<MovieResponse> = _nowPlayingMovies

    private val _topRatedMovies: MutableStateFlow<MovieResponse> =
        MutableStateFlow(MovieResponse(emptyList()))
    val topRatedMovies: StateFlow<MovieResponse> = _topRatedMovies

    private val _providers: MutableStateFlow<ProvidersResponse> =
        MutableStateFlow(ProvidersResponse(emptyList()))
    val providers: StateFlow<ProvidersResponse> = _providers

    private val _movieDetails = MutableStateFlow<DetailsMovieResponse?>(null)
    val movieDetails: StateFlow<DetailsMovieResponse?> = _movieDetails

    private val _movieCredits = MutableStateFlow<MovieCreditsResponse?>(null)
    val movieCredits: StateFlow<MovieCreditsResponse?> = _movieCredits

    private val _movieImageList = MutableStateFlow<ImageBackdrop?>(null)
    val movieImageList: StateFlow<ImageBackdrop?> = _movieImageList

    private val _genres = _movieDetails.value?.genres?.map { it.nameGenre }
        ?.let { MutableStateFlow<List<String>>(it) }
    val genres: StateFlow<List<String>>? = _genres

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

    fun getMovieDetailsById(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieDetails.value = getMovieDetailsByIdUseCase(movieId)
        }
    }

    fun getMovieCreditsById(movieId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _movieCredits.value = getMovieCreditsByIdUseCase(movieId)
        }
    }

    fun getMovieImageListById(movieId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _movieImageList.value = getMovieImageListByIdUseCase(movieId)
        }
    }

    fun getRunTimeInHours(minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return "${hours}h ${remainingMinutes}m"
    }

    fun formatPrice(value: Long): String {
        val formatter = NumberFormat.getInstance(Locale("es", "AR"))
        return formatter.format(value)
    }



}

