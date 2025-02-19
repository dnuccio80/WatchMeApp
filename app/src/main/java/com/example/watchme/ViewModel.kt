package com.example.watchme

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchme.app.data.network.responses.SeasonDetails
import com.example.watchme.app.data.network.responses.SeriesDetailsResponse
import com.example.watchme.app.domain.movies.GetImageListByIdUseCase
import com.example.watchme.app.domain.movies.GetMovieCreditsByIdUseCase
import com.example.watchme.app.domain.movies.GetMovieDetailsByIdUseCase
import com.example.watchme.app.domain.movies.GetNowPlayingMoviesUseCase
import com.example.watchme.app.domain.movies.GetPopularMoviesUseCase
import com.example.watchme.app.domain.movies.GetRecommendationsByIdUseCase
import com.example.watchme.app.domain.movies.GetReviewsByIdUseCase
import com.example.watchme.app.domain.movies.GetTopRatedMoviesUseCase
import com.example.watchme.app.domain.movies.GetUpcomingMoviesUseCase
import com.example.watchme.app.domain.movies.GetVideosByIdUseCase
import com.example.watchme.app.domain.providers.GetProvidersUseCase
import com.example.watchme.app.domain.series.GetAiringSeriesTodayUseCase
import com.example.watchme.app.domain.series.GetOnTheAirSeriesUseCase
import com.example.watchme.app.domain.series.GetPopularSeriesUseCase
import com.example.watchme.app.domain.series.GetSeasonDetailsUseCase
import com.example.watchme.app.domain.series.GetSeriesDetailsByIdUseCase
import com.example.watchme.app.domain.series.GetTopRatedSeriesUseCase
import com.example.watchme.app.ui.dataClasses.BackdropImageDataClass
import com.example.watchme.app.ui.dataClasses.DetailsMovieDataClass
import com.example.watchme.app.ui.dataClasses.EpisodeDetailsDataClass
import com.example.watchme.app.ui.dataClasses.EpisodesDataClass
import com.example.watchme.app.ui.dataClasses.MovieCreditsDataClass
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import com.example.watchme.app.ui.dataClasses.ProvidersDataClass
import com.example.watchme.app.ui.dataClasses.ReviewDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDetailsDataClass
import com.example.watchme.app.ui.dataClasses.VideoDataClass
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
    //  MOVIES
    getPopularMoviesUseCase: GetPopularMoviesUseCase,
    getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    getProvidersUseCase: GetProvidersUseCase,
    getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getMovieDetailsByIdUseCase: GetMovieDetailsByIdUseCase,
    private val getMovieCreditsByIdUseCase: GetMovieCreditsByIdUseCase,
    private val getMovieImageListByIdUseCase: GetImageListByIdUseCase,
    private val getRecommendationsByIdUseCase: GetRecommendationsByIdUseCase,
    private val getReviewsByIdUseCase: GetReviewsByIdUseCase,
    private val getVideosByIdUseCase: GetVideosByIdUseCase,

    // SERIES
    getPopularSeriesUseCase: GetPopularSeriesUseCase,
    getAiringSeriesTodayUseCase: GetAiringSeriesTodayUseCase,
    getOnTheAirSeriesUseCase: GetOnTheAirSeriesUseCase,
    getTopRatedSeriesUseCase: GetTopRatedSeriesUseCase,
    private val getSeriesDetailsByIdUseCase: GetSeriesDetailsByIdUseCase,
    private val getSeasonDetailsUseCase: GetSeasonDetailsUseCase,

) : ViewModel() {

    // MOVIES

    private val _popularMovies: MutableStateFlow<List<MovieDataClass>> = MutableStateFlow(emptyList())
    val popularMovies: StateFlow<List<MovieDataClass>> = _popularMovies

    private val _nowPlayingMovies: MutableStateFlow<List<MovieDataClass>> = MutableStateFlow(emptyList())
    val nowPlayingMovies: StateFlow<List<MovieDataClass>> = _nowPlayingMovies

    private val _topRatedMovies: MutableStateFlow<List<MovieDataClass>> = MutableStateFlow(emptyList())
    val topRatedMovies: StateFlow<List<MovieDataClass>> = _topRatedMovies

    private val _upcomingMovies: MutableStateFlow<List<MovieDataClass>> = MutableStateFlow(emptyList())
    val upcomingMovies: StateFlow<List<MovieDataClass>> = _upcomingMovies

    private val _providers: MutableStateFlow<List<ProvidersDataClass>> = MutableStateFlow(emptyList())
    val providers: StateFlow<List<ProvidersDataClass>> = _providers

    private val _movieDetails = MutableStateFlow<DetailsMovieDataClass?>(null)
    val movieDetails: StateFlow<DetailsMovieDataClass?> = _movieDetails

    private val _movieCredits = MutableStateFlow<MovieCreditsDataClass?>(null)
    val movieCredits: StateFlow<MovieCreditsDataClass?> = _movieCredits

    private val _movieImageList = MutableStateFlow<List<BackdropImageDataClass>?>(null)
    val movieImageList: StateFlow<List<BackdropImageDataClass>?> = _movieImageList


    private val _genres = _movieDetails.value?.genres?.map { it.nameGenre }
        ?.let { MutableStateFlow<List<String>>(it) }
    val genres: StateFlow<List<String>>? = _genres

    private val _recommendations = MutableStateFlow<List<MovieDataClass>?>(null)
    val recommendations: StateFlow<List<MovieDataClass>?> = _recommendations

    private val _reviews = MutableStateFlow<List<ReviewDataClass>?>(null)
    val reviews : StateFlow<List<ReviewDataClass>?> = _reviews

    private val _videos = MutableStateFlow<List<VideoDataClass>?>(null)
    val videos : StateFlow<List<VideoDataClass>?> = _videos

    // SERIES

    private val _popularSeries = MutableStateFlow<List<SeriesDataClass>?>(null)
    val popularSeries : StateFlow<List<SeriesDataClass>?> = _popularSeries

    private val _airingSeriesToday = MutableStateFlow<List<SeriesDataClass>?>(null)
    val airingSeriesToday : StateFlow<List<SeriesDataClass>?> = _airingSeriesToday

    private val _onTheAirSeries = MutableStateFlow<List<SeriesDataClass>?>(null)
    val onTheAirSeries : StateFlow<List<SeriesDataClass>?> = _onTheAirSeries

    private val _topRatedSeries = MutableStateFlow<List<SeriesDataClass>?>(null)
    val topRatedSeries : StateFlow<List<SeriesDataClass>?> = _topRatedSeries

    private val _seriesDetails = MutableStateFlow<SeriesDetailsDataClass?>(null)
    val seriesDetails: StateFlow<SeriesDetailsDataClass?> = _seriesDetails

    private val _seasonsDetails = MutableStateFlow<List<EpisodeDetailsDataClass>?>(null)
    val seasonsDetails: StateFlow<List<EpisodeDetailsDataClass>?> = _seasonsDetails

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _popularMovies.value = getPopularMoviesUseCase()
            _nowPlayingMovies.value = getNowPlayingMoviesUseCase()
            _topRatedMovies.value = getTopRatedMoviesUseCase()
            _providers.value = getProvidersUseCase()
            _upcomingMovies.value = getUpcomingMoviesUseCase()
            _popularSeries.value = getPopularSeriesUseCase()
            _airingSeriesToday.value = getAiringSeriesTodayUseCase()
            _onTheAirSeries.value = getOnTheAirSeriesUseCase()
            _topRatedSeries.value = getTopRatedSeriesUseCase()
        }
    }

    fun getPercentageColor(voteAverage: Int): Color {
        return when (voteAverage) {
            in 0..40 -> NegativeVoteColor
            in 41..69 -> IntermediateVoteColor
            else -> PositiveVoteColor
        }
    }

    // MOVIES

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

    fun getRecommendationsById(movieId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _recommendations.value = getRecommendationsByIdUseCase(movieId)
        }
    }

    fun getReviewsById(movieId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _reviews.value = getReviewsByIdUseCase(movieId)
        }
    }

    fun getVideosById(movieId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _videos.value = getVideosByIdUseCase(movieId)
        }
    }

    // SERIES

    fun getSeriesDetailsById(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _seriesDetails.value = getSeriesDetailsByIdUseCase(seriesId)
        }
    }

    fun getSeasonDetails(seriesId:Int, seasonNumber:Int){
        viewModelScope.launch(Dispatchers.IO) {
            _seasonsDetails.value = getSeasonDetailsUseCase(seriesId, seasonNumber)
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

