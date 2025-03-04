package com.example.watchme

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchme.app.data.network.responses.RatingResponse
import com.example.watchme.app.domain.collections.GetCollectionDetailsByIdUseCase
import com.example.watchme.app.domain.episodes.GetEpisodeDetailsByIdUseCase
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
import com.example.watchme.app.domain.rating.RateMovieUseCase
import com.example.watchme.app.domain.searches.GetSearchCollectionUseCase
import com.example.watchme.app.domain.searches.GetSearchMovieUseCase
import com.example.watchme.app.domain.searches.GetSearchPeopleUseCase
import com.example.watchme.app.domain.searches.GetSearchSeriesUseCase
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
import com.example.watchme.app.ui.dataClasses.CollectionDetailsDataClass
import com.example.watchme.app.ui.dataClasses.SearchDataClass
import com.example.watchme.app.ui.dataClasses.DetailsMovieDataClass
import com.example.watchme.app.ui.dataClasses.EpisodeDetailsDataClass
import com.example.watchme.app.ui.dataClasses.CreditsDataClass
import com.example.watchme.app.ui.dataClasses.EpisodesDetailsDataClass
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import com.example.watchme.app.ui.dataClasses.PeopleDetailsDataClass
import com.example.watchme.app.ui.dataClasses.PeopleMovieInterpretationDataClass
import com.example.watchme.app.ui.dataClasses.PeopleSeriesInterpretationDataClass
import com.example.watchme.app.ui.dataClasses.ProvidersDataClass
import com.example.watchme.app.ui.dataClasses.ReviewDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDetailsDataClass
import com.example.watchme.app.ui.dataClasses.VideoDataClass
import com.example.watchme.core.SearchTypes
import com.example.watchme.ui.theme.IntermediateVoteColor
import com.example.watchme.ui.theme.NegativeVoteColor
import com.example.watchme.ui.theme.PositiveVoteColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    // COLLECTIONS

    private val getCollectionDetailsByIdUseCase: GetCollectionDetailsByIdUseCase,

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

    // EPISODES

    private val getEpisodeDetailsByIdUseCase: GetEpisodeDetailsByIdUseCase,

    // PEOPLE

    private val getPeopleDetailsByIdUseCase: GetPeopleDetailsByIdUseCase,
    private val getPeopleMovieInterpretationsByIdUseCase: GetPeopleMovieInterpretationsByIdUseCase,
    private val getPeopleSeriesInterpretationsByIdUseCase: GetPeopleSeriesInterpretationsByIdUseCase,
    private val getPeopleMediaByIdUseCase: GetPeopleMediaByIdUseCase,

    // SEARCHES

    private val getSearchCollectionUseCase: GetSearchCollectionUseCase,
    private val getSearchMovieUseCase: GetSearchMovieUseCase,
    private val getSearchSeriesUseCase: GetSearchSeriesUseCase,
    private val getSearchPeopleUseCase: GetSearchPeopleUseCase,

    // RATING

    private val rateMovieUseCase: RateMovieUseCase,


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

    // COLLECTIONS

    private val _collectionDetails = MutableStateFlow<CollectionDetailsDataClass?>(null)
    val collectionDetails: StateFlow<CollectionDetailsDataClass?> = _collectionDetails

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

    // EPISODES

    private val _episodeDetails = MutableStateFlow<EpisodesDetailsDataClass?>(null)
    val episodeDetails: StateFlow<EpisodesDetailsDataClass?> = _episodeDetails

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

    // SEARCHES

    private val _searchCollection = MutableStateFlow<List<SearchDataClass>?>(null)
    val searchCollection : StateFlow<List<SearchDataClass>?> = _searchCollection

    private val _searchMovie = MutableStateFlow<List<SearchDataClass>?>(null)
    val searchMovie : StateFlow<List<SearchDataClass>?> = _searchMovie

    private val _searchSeries =  MutableStateFlow<List<SearchDataClass>?>(null)
    val searchSeries  : StateFlow<List<SearchDataClass>?> = _searchSeries

    private val _searchPeople = MutableStateFlow<List<SearchDataClass>?>(null)
    val searchPeople  : StateFlow<List<SearchDataClass>?> = _searchPeople

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _searchTypeSelected = MutableStateFlow(SearchTypes.Collections.title)
    val searchTypeSelected: StateFlow<String> = _searchTypeSelected

    // RATING

    private val _rating = MutableStateFlow<RatingResponse?>(null)
    val rating: StateFlow<RatingResponse?> = _rating

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
        observeSearchQuery()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeSearchQuery() {

        val useCase: suspend (String) -> List<SearchDataClass> = when (_searchTypeSelected.value) {
            SearchTypes.Collections.title -> { query -> getSearchCollectionUseCase(query) }
            SearchTypes.Movies.title -> { query -> getSearchMovieUseCase(query) }
            SearchTypes.TvSeries.title -> { query -> getSearchSeriesUseCase(query) }
            else -> { query -> getSearchPeopleUseCase(query) }
        }

        val searchType = when (_searchTypeSelected.value) {
            SearchTypes.Collections.title -> _searchCollection
            SearchTypes.Movies.title -> _searchMovie
            SearchTypes.TvSeries.title -> _searchSeries
            else -> _searchPeople
        }

        _query.debounce(500)
            .distinctUntilChanged()
            .flatMapLatest {query ->
                flow {
                    try {
                        emit(useCase(query))
                    } catch (e: Exception) {
                        emit(emptyList())
                    }
                }
            }
            .flowOn(Dispatchers.IO)
            .onEach { results -> searchType.value = results }
            .launchIn(viewModelScope)
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    fun onSearchTypeSelectedChange(newType:String){
        _searchTypeSelected.value = newType
        observeSearchQuery()
    }

    fun getPercentageColor(voteAverage: Int): Color {
        return when (voteAverage) {
            in 0..40 -> NegativeVoteColor
            in 41..69 -> IntermediateVoteColor
            else -> PositiveVoteColor
        }
    }

    fun getRunTimeInHours(minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return if(hours != 0) "${hours}h ${remainingMinutes}m"
        else "${remainingMinutes}m"
    }

    fun formatPrice(value: Long): String {
        val formatter = NumberFormat.getInstance(Locale("es", "AR"))
        return formatter.format(value)
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

    // COLLECTIONS

    fun getCollectionDetailsById(collectionId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _collectionDetails.value = getCollectionDetailsByIdUseCase(collectionId)
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

    // EPISODES

    fun getEpisodeDetailsById(seriesId: Int, seasonNumber: Int, episodeNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _episodeDetails.value = getEpisodeDetailsByIdUseCase(seriesId, seasonNumber, episodeNumber)
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

    // RATING

    fun rateMovie(value:Float, movieId:Int){
        viewModelScope.launch(Dispatchers.IO) {
            _rating.value = rateMovieUseCase(rating = value, movieId = movieId)
        }
    }


}

