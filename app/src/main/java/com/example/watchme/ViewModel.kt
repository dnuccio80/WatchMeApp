package com.example.watchme

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchme.app.data.network.responses.toPeopleSeriesInterpretationDataClass
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
import com.example.watchme.app.domain.people.GetPeopleDetailsByIdUseCase
import com.example.watchme.app.domain.people.GetPeopleMediaByIdUseCase
import com.example.watchme.app.domain.people.GetPeopleMovieInterpretationsByIdUseCase
import com.example.watchme.app.domain.people.GetPeopleSeriesInterpretationsByIdUseCase
import com.example.watchme.app.domain.providers.GetProvidersUseCase
import com.example.watchme.app.domain.series.GetAiringSeriesTodayUseCase
import com.example.watchme.app.domain.series.GetOnTheAirSeriesUseCase
import com.example.watchme.app.domain.series.GetPopularSeriesUseCase
import com.example.watchme.app.domain.series.GetSeasonDetailsUseCase
import com.example.watchme.app.domain.series.GetSeriesCreditsByIdUseCase
import com.example.watchme.app.domain.series.GetSeriesDetailsByIdUseCase
import com.example.watchme.app.domain.series.GetSeriesImageListByIdUseCase
import com.example.watchme.app.domain.series.GetSeriesRecommendationsByIdUseCase
import com.example.watchme.app.domain.series.GetSeriesVideosByIdUseCase
import com.example.watchme.app.domain.series.GetTopRatedSeriesUseCase
import com.example.watchme.app.ui.dataClasses.BackdropImageDataClass
import com.example.watchme.app.ui.dataClasses.DetailsMovieDataClass
import com.example.watchme.app.ui.dataClasses.EpisodeDetailsDataClass
import com.example.watchme.app.ui.dataClasses.CreditsDataClass
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import com.example.watchme.app.ui.dataClasses.PeopleDetailsDataClass
import com.example.watchme.app.ui.dataClasses.PeopleMovieInterpretationDataClass
import com.example.watchme.app.ui.dataClasses.PeopleSeriesInterpretationDataClass
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
    private val getSeriesRecommendationsByIdUseCase: GetSeriesRecommendationsByIdUseCase,
    private val getSeriesImageListByIdUseCase: GetSeriesImageListByIdUseCase,
    private val getSeriesVideosByIdUseCase: GetSeriesVideosByIdUseCase,
    private val getSeriesCreditsByIdUseCase: GetSeriesCreditsByIdUseCase,

    // PEOPLE

    private val getPeopleDetailsByIdUseCase: GetPeopleDetailsByIdUseCase,
    private val getPeopleMovieInterpretationsByIdUseCase: GetPeopleMovieInterpretationsByIdUseCase,
    private val getPeopleSeriesInterpretationsByIdUseCase: GetPeopleSeriesInterpretationsByIdUseCase,
    private val getPeopleMediaByIdUseCase: GetPeopleMediaByIdUseCase,

    ) : ViewModel() {

    // MOVIES

    private val _popularMovies: MutableStateFlow<List<MovieDataClass>> =
        MutableStateFlow(emptyList())
    val popularMovies: StateFlow<List<MovieDataClass>> = _popularMovies

    private val _nowPlayingMovies: MutableStateFlow<List<MovieDataClass>> =
        MutableStateFlow(emptyList())
    val nowPlayingMovies: StateFlow<List<MovieDataClass>> = _nowPlayingMovies

    private val _topRatedMovies: MutableStateFlow<List<MovieDataClass>> =
        MutableStateFlow(emptyList())
    val topRatedMovies: StateFlow<List<MovieDataClass>> = _topRatedMovies

    private val _upcomingMovies: MutableStateFlow<List<MovieDataClass>> =
        MutableStateFlow(emptyList())
    val upcomingMovies: StateFlow<List<MovieDataClass>> = _upcomingMovies

    private val _providers: MutableStateFlow<List<ProvidersDataClass>> =
        MutableStateFlow(emptyList())
    val providers: StateFlow<List<ProvidersDataClass>> = _providers

    private val _movieDetails = MutableStateFlow<DetailsMovieDataClass?>(null)
    val movieDetails: StateFlow<DetailsMovieDataClass?> = _movieDetails

    private val _movieCredits = MutableStateFlow<CreditsDataClass?>(null)
    val movieCredits: StateFlow<CreditsDataClass?> = _movieCredits

    private val _movieImageList = MutableStateFlow<List<BackdropImageDataClass>?>(null)
    val movieImageList: StateFlow<List<BackdropImageDataClass>?> = _movieImageList


    private val _genres = _movieDetails.value?.genres?.map { it.nameGenre }
        ?.let { MutableStateFlow<List<String>>(it) }
    val genres: StateFlow<List<String>>? = _genres

    private val _movieRecommendations = MutableStateFlow<List<MovieDataClass>?>(null)
    val movieRecommendations: StateFlow<List<MovieDataClass>?> = _movieRecommendations

    private val _reviews = MutableStateFlow<List<ReviewDataClass>?>(null)
    val reviews: StateFlow<List<ReviewDataClass>?> = _reviews

    private val _movieVideos = MutableStateFlow<List<VideoDataClass>?>(null)
    val movieVideos: StateFlow<List<VideoDataClass>?> = _movieVideos

    // SERIES

    private val _popularSeries = MutableStateFlow<List<SeriesDataClass>?>(null)
    val popularSeries: StateFlow<List<SeriesDataClass>?> = _popularSeries

    private val _airingSeriesToday = MutableStateFlow<List<SeriesDataClass>?>(null)
    val airingSeriesToday: StateFlow<List<SeriesDataClass>?> = _airingSeriesToday

    private val _onTheAirSeries = MutableStateFlow<List<SeriesDataClass>?>(null)
    val onTheAirSeries: StateFlow<List<SeriesDataClass>?> = _onTheAirSeries

    private val _topRatedSeries = MutableStateFlow<List<SeriesDataClass>?>(null)
    val topRatedSeries: StateFlow<List<SeriesDataClass>?> = _topRatedSeries

    private val _seriesDetails = MutableStateFlow<SeriesDetailsDataClass?>(null)
    val seriesDetails: StateFlow<SeriesDetailsDataClass?> = _seriesDetails

    private val _seasonsDetails = MutableStateFlow<List<EpisodeDetailsDataClass>?>(null)
    val seasonsDetails: StateFlow<List<EpisodeDetailsDataClass>?> = _seasonsDetails

    private val _seriesRecommendations = MutableStateFlow<List<SeriesDataClass>?>(null)
    val seriesRecommendations: StateFlow<List<SeriesDataClass>?> = _seriesRecommendations

    private val _seriesImageList = MutableStateFlow<List<BackdropImageDataClass>?>(null)
    val seriesImageList: StateFlow<List<BackdropImageDataClass>?> = _seriesImageList

    private val _seriesVideos = MutableStateFlow<List<VideoDataClass>?>(null)
    val seriesVideos: StateFlow<List<VideoDataClass>?> = _seriesVideos

    private val _seriesCredits = MutableStateFlow<CreditsDataClass?>(null)
    val seriesCredits: StateFlow<CreditsDataClass?> = _seriesCredits

    // PEOPLE

    private val _peopleDetails = MutableStateFlow<PeopleDetailsDataClass?>(null)
    val peopleDetails: StateFlow<PeopleDetailsDataClass?> = _peopleDetails

    private val _peopleMovieInterpretations =
        MutableStateFlow<PeopleMovieInterpretationDataClass?>(null)
    val peopleMovieInterpretations: StateFlow<PeopleMovieInterpretationDataClass?> =
        _peopleMovieInterpretations

    private val _peopleSeriesInterpretations =
        MutableStateFlow<PeopleSeriesInterpretationDataClass?>(null)
    val peopleSeriesInterpretations: StateFlow<PeopleSeriesInterpretationDataClass?> = _peopleSeriesInterpretations

    private val _peopleMediaImages = MutableStateFlow<List<BackdropImageDataClass>?>(null)
    val peopleMediaImages : StateFlow<List<BackdropImageDataClass>?> = _peopleMediaImages

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

    fun getMovieCreditsById(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieCredits.value = getMovieCreditsByIdUseCase(movieId)
        }
    }

    fun getMovieImageListById(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieImageList.value = getMovieImageListByIdUseCase(movieId)
        }
    }

    fun getRecommendationsById(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieRecommendations.value = getRecommendationsByIdUseCase(movieId)
        }
    }

    fun getReviewsById(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _reviews.value = getReviewsByIdUseCase(movieId)
        }
    }

    fun getVideosById(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieVideos.value = getVideosByIdUseCase(movieId)
        }
    }

    // SERIES

    fun getSeriesDetailsById(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _seriesDetails.value = getSeriesDetailsByIdUseCase(seriesId)
        }
    }

    fun getSeasonDetailsById(seriesId: Int, seasonNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _seasonsDetails.value = getSeasonDetailsUseCase(seriesId, seasonNumber)
        }
    }

    fun getSeriesRecommendationsById(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _seriesRecommendations.value = getSeriesRecommendationsByIdUseCase(seriesId)
        }
    }

    fun getSeriesImageListById(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _seriesImageList.value = getSeriesImageListByIdUseCase(seriesId)
        }
    }

    fun getSeriesVideosListById(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _seriesVideos.value = getSeriesVideosByIdUseCase(seriesId)
        }
    }

    fun getSeriesCreditsById(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _seriesCredits.value = getSeriesCreditsByIdUseCase(seriesId)
        }
    }

    // PEOPLE

    fun getPeopleDetailsById(personId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _peopleDetails.value = getPeopleDetailsByIdUseCase(personId)
        }
    }

    fun getPeopleMovieInterpretationsById(personId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _peopleMovieInterpretations.value = getPeopleMovieInterpretationsByIdUseCase(personId)
        }
    }

    fun getPeopleSeriesInterpretationsById(personId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _peopleSeriesInterpretations.value = getPeopleSeriesInterpretationsByIdUseCase(personId)
        }
    }

    fun getPeopleMediaById(personId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _peopleMediaImages.value = getPeopleMediaByIdUseCase(personId)
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

