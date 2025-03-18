package com.example.watchme

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchme.app.data.network.responses.Dtos.DeleteItemFromListDto
import com.example.watchme.app.data.network.responses.MovieProvidersResponse
import com.example.watchme.app.data.network.responses.TypeProvider
import com.example.watchme.app.domain.favorites.AddFavoriteUseCase
import com.example.watchme.app.domain.lists.AddToWatchlistUseCase
import com.example.watchme.app.domain.lists.CreateListUseCase
import com.example.watchme.app.domain.account.GetAccountDetailsUseCase
import com.example.watchme.app.domain.favorites.GetFavoritesCountUseCase
import com.example.watchme.app.domain.favorites.GetFavoritesMoviesUseCase
import com.example.watchme.app.domain.favorites.GetFavoritesSeriesUseCase
import com.example.watchme.app.domain.lists.GetListDetailsUseCase
import com.example.watchme.app.domain.lists.GetMyListsUseCase
import com.example.watchme.app.domain.account.GetRatedEpisodesUseCase
import com.example.watchme.app.domain.account.GetRatedMoviesUseCase
import com.example.watchme.app.domain.account.GetRatedSeriesUseCase
import com.example.watchme.app.domain.lists.GetWatchlistMoviesUseCase
import com.example.watchme.app.domain.lists.GetWatchlistSeriesUseCase
import com.example.watchme.app.domain.collections.GetCollectionDetailsByIdUseCase
import com.example.watchme.app.domain.episodes.GetEpisodeDetailsByIdUseCase
import com.example.watchme.app.domain.lists.ClearListUseCase
import com.example.watchme.app.domain.lists.DeleteItemFromListUseCase
import com.example.watchme.app.domain.lists.DeleteListUseCase
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
import com.example.watchme.app.domain.providers.GetMovieProvidersByMovieIdUseCase
import com.example.watchme.app.domain.providers.GetProvidersUseCase
import com.example.watchme.app.domain.rating.DeleteRateMovieUseCase
import com.example.watchme.app.domain.rating.DeleteRateSeriesEpisodeUseCase
import com.example.watchme.app.domain.rating.DeleteRateSeriesUseCase
import com.example.watchme.app.domain.rating.GetRatedCountUseCase
import com.example.watchme.app.domain.rating.RateMovieUseCase
import com.example.watchme.app.domain.rating.RateSeriesEpisodeUseCase
import com.example.watchme.app.domain.rating.RateSeriesUseCase
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
import com.example.watchme.app.ui.dataClasses.AccountDetailsDataClass
import com.example.watchme.app.ui.dataClasses.BackdropImageDataClass
import com.example.watchme.app.ui.dataClasses.CollectionDetailsDataClass
import com.example.watchme.app.ui.dataClasses.CreateListDataClass
import com.example.watchme.app.ui.dataClasses.SearchDataClass
import com.example.watchme.app.ui.dataClasses.DetailsMovieDataClass
import com.example.watchme.app.ui.dataClasses.EpisodeDetailsDataClass
import com.example.watchme.app.ui.dataClasses.CreditsDataClass
import com.example.watchme.app.ui.dataClasses.EpisodesDetailsDataClass
import com.example.watchme.app.ui.dataClasses.EpisodesRatedDataClass
import com.example.watchme.app.ui.dataClasses.FavoriteDataClass
import com.example.watchme.app.ui.dataClasses.ListDataClass
import com.example.watchme.app.ui.dataClasses.ListDetailsDataClass
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import com.example.watchme.app.ui.dataClasses.PeopleDetailsDataClass
import com.example.watchme.app.ui.dataClasses.PeopleMovieInterpretationDataClass
import com.example.watchme.app.ui.dataClasses.PeopleSeriesInterpretationDataClass
import com.example.watchme.app.ui.dataClasses.ProvidersDataClass
import com.example.watchme.app.ui.dataClasses.RatedItemDataClass
import com.example.watchme.app.ui.dataClasses.RatingRequestDataClass
import com.example.watchme.app.ui.dataClasses.ReviewDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDetailsDataClass
import com.example.watchme.app.ui.dataClasses.TotalRatedResultsDataClass
import com.example.watchme.app.ui.dataClasses.VideoDataClass
import com.example.watchme.app.ui.dataClasses.RequestResponseDataClass
import com.example.watchme.core.Categories
import com.example.watchme.core.LocaleHelper
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
    private val deleteRateMovieUseCase: DeleteRateMovieUseCase,
    private val rateSeriesUseCase: RateSeriesUseCase,
    private val deleteRateSeriesUseCase: DeleteRateSeriesUseCase,
    private val rateSeriesEpisodeUseCase: RateSeriesEpisodeUseCase,
    private val deleteRateSeriesEpisodeUseCase: DeleteRateSeriesEpisodeUseCase,
    private val getRatedCountUseCase: GetRatedCountUseCase,

    // ACCOUNT

    private val getRatedMoviesUseCase: GetRatedMoviesUseCase,
    private val getRatedSeriesUseCase: GetRatedSeriesUseCase,
    private val getRatedEpisodesUseCase: GetRatedEpisodesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val getFavoritesMoviesUseCase: GetFavoritesMoviesUseCase,
    private val getFavoritesSeriesUseCase: GetFavoritesSeriesUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val getWatchlistMoviesUseCase: GetWatchlistMoviesUseCase,
    private val getWatchlistSeriesUseCase: GetWatchlistSeriesUseCase,
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase,
    private val getMyListsUseCase: GetMyListsUseCase,
    private val createListUseCase: CreateListUseCase,
    private val getListDetailsUseCase: GetListDetailsUseCase,
    private val getFavoritesCountUseCase: GetFavoritesCountUseCase,
    private val deleteListUseCase: DeleteListUseCase,
    private val deleteItemFromListUseCase: DeleteItemFromListUseCase,
    private val clearListUseCase: ClearListUseCase,

    // PROVIDERS

    private val getMovieProvidersByMovieIdUseCase: GetMovieProvidersByMovieIdUseCase,

    ) : ViewModel() {

        // GENERAL SETTINGS

    private var defaultLanguage = Locale.getDefault().language
    private var defaultCountry = Locale.getDefault().country

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
    val peopleSeriesInterpretations: StateFlow<PeopleSeriesInterpretationDataClass?> =
        _peopleSeriesInterpretations

    private val _peopleMediaImages = MutableStateFlow<List<BackdropImageDataClass>?>(null)
    val peopleMediaImages: StateFlow<List<BackdropImageDataClass>?> = _peopleMediaImages

    // SEARCHES

    private val _searchCollection = MutableStateFlow<List<SearchDataClass>?>(null)
    val searchCollection: StateFlow<List<SearchDataClass>?> = _searchCollection

    private val _searchMovie = MutableStateFlow<List<SearchDataClass>?>(null)
    val searchMovie: StateFlow<List<SearchDataClass>?> = _searchMovie

    private val _searchSeries = MutableStateFlow<List<SearchDataClass>?>(null)
    val searchSeries: StateFlow<List<SearchDataClass>?> = _searchSeries

    private val _searchPeople = MutableStateFlow<List<SearchDataClass>?>(null)
    val searchPeople: StateFlow<List<SearchDataClass>?> = _searchPeople

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _searchTypeSelected = MutableStateFlow((Categories.Collections.title))
    val searchTypeSelected: StateFlow<Int> = _searchTypeSelected

    // RATING

    private val _rating = MutableStateFlow<RatingRequestDataClass?>(null)
    val rating: StateFlow<RatingRequestDataClass?> = _rating

    private val _totalRatingCount = MutableStateFlow<TotalRatedResultsDataClass?>(null)
    val totalRatingCount: StateFlow<TotalRatedResultsDataClass?> = _totalRatingCount

    // ACCOUNT

    private val _ratedMovies = MutableStateFlow<List<RatedItemDataClass>?>(null)
    val ratedMovies: StateFlow<List<RatedItemDataClass>?> = _ratedMovies

    private val _ratedSeries = MutableStateFlow<List<RatedItemDataClass>?>(null)
    val ratedSeries: StateFlow<List<RatedItemDataClass>?> = _ratedSeries

    private val _ratedSeriesEpisodes = MutableStateFlow<List<EpisodesRatedDataClass>?>(null)
    val ratedSeriesEpisodes: StateFlow<List<EpisodesRatedDataClass>?> = _ratedSeriesEpisodes

    private val _addFavoriteRequest = MutableStateFlow<FavoriteDataClass?>(null)
    val addFavoriteRequest: StateFlow<FavoriteDataClass?> = _addFavoriteRequest

    private val _favoritesMovies = MutableStateFlow<List<MovieDataClass>?>(null)
    val favoritesMovies: StateFlow<List<MovieDataClass>?> = _favoritesMovies

    private val _favoritesSeries = MutableStateFlow<List<SeriesDataClass>?>(null)
    val favoritesSeries: StateFlow<List<SeriesDataClass>?> = _favoritesSeries

    private val _watchListRequest = MutableStateFlow<RequestResponseDataClass?>(null)
    val watchListRequest: StateFlow<RequestResponseDataClass?> = _watchListRequest

    private val _watchlistMovies = MutableStateFlow<List<MovieDataClass>?>(null)
    val watchlistMovies: StateFlow<List<MovieDataClass>?> = _watchlistMovies

    private val _watchlistSeries = MutableStateFlow<List<SeriesDataClass>?>(null)
    val watchlistSeries: StateFlow<List<SeriesDataClass>?> = _watchlistSeries

    private val _accountDetails = MutableStateFlow<AccountDetailsDataClass?>(null)
    val accountDetails: StateFlow<AccountDetailsDataClass?> = _accountDetails

    private val _myLists = MutableStateFlow<List<ListDataClass>?>(null)
    val myLists: StateFlow<List<ListDataClass>?> = _myLists

    private val _createListRequest = MutableStateFlow<CreateListDataClass?>(null)
    val createListRequest: StateFlow<CreateListDataClass?> = _createListRequest

    private val _listDetails = MutableStateFlow<ListDetailsDataClass?>(null)
    val listDetails: StateFlow<ListDetailsDataClass?> = _listDetails

    private val _favoritesCount = MutableStateFlow<Int?>(null)
    val favoritesCount: StateFlow<Int?> = _favoritesCount

    private val _deleteListRequest = MutableStateFlow<RequestResponseDataClass?>(null)
    val deleteListRequest: StateFlow<RequestResponseDataClass?> = _deleteListRequest

    private val _deleteItemFromListRequest = MutableStateFlow<RequestResponseDataClass?>(null)
    val deleteItemFromListRequest: StateFlow<RequestResponseDataClass?> = _deleteItemFromListRequest

    private val _clearListRequest = MutableStateFlow<RequestResponseDataClass?>(null)
    val clearListRequest: StateFlow<RequestResponseDataClass?> = _clearListRequest

    // PROVIDERS

    private val _movieProviders = MutableStateFlow<MovieProvidersResponse?>(null)
    val movieProviders: StateFlow<MovieProvidersResponse?> = _movieProviders

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getDefaultLanguage()
            _popularMovies.value = getPopularMoviesUseCase(defaultLanguage, defaultCountry)
            _nowPlayingMovies.value = getNowPlayingMoviesUseCase(defaultLanguage, defaultCountry)
            _topRatedMovies.value = getTopRatedMoviesUseCase(defaultLanguage, defaultCountry)
            _providers.value = getProvidersUseCase(defaultLanguage, defaultCountry)
            _upcomingMovies.value = getUpcomingMoviesUseCase(defaultLanguage, defaultCountry)
            _popularSeries.value = getPopularSeriesUseCase(defaultLanguage)
            _airingSeriesToday.value = getAiringSeriesTodayUseCase(defaultLanguage)
            _onTheAirSeries.value = getOnTheAirSeriesUseCase(defaultLanguage)
            _topRatedSeries.value = getTopRatedSeriesUseCase(defaultLanguage)
            _favoritesMovies.value = getFavoritesMoviesUseCase(0)
            _favoritesSeries.value = getFavoritesSeriesUseCase(0)
            _watchlistSeries.value = getWatchlistSeriesUseCase(0)
            _watchlistMovies.value = getWatchlistMoviesUseCase(0)
            _accountDetails.value = getAccountDetailsUseCase(0)
            _ratedMovies.value = getRatedMoviesUseCase(0)
            _ratedSeries.value = getRatedSeriesUseCase(0)
            _ratedSeriesEpisodes.value = getRatedEpisodesUseCase(0)
        }
        observeSearchQuery()
    }

    private fun getDefaultLanguage() {
        Log.i("Damian", "Language: $defaultLanguage, Country: $defaultCountry")
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeSearchQuery() {

        val useCase: suspend (String) -> List<SearchDataClass> = when (_searchTypeSelected.value) {
            Categories.Collections.title -> { query -> getSearchCollectionUseCase(query, defaultLanguage, defaultCountry) }
            Categories.Movies.title -> { query -> getSearchMovieUseCase(query, defaultLanguage, defaultCountry) }
            Categories.TvSeries.title -> { query -> getSearchSeriesUseCase(query, defaultLanguage, defaultCountry) }
            else -> { query -> getSearchPeopleUseCase(query, defaultLanguage, defaultCountry) }
        }

        val searchType = when (_searchTypeSelected.value) {
            Categories.Collections.title -> _searchCollection
            Categories.Movies.title -> _searchMovie
            Categories.TvSeries.title -> _searchSeries
            else -> _searchPeople
        }

        _query.debounce(500)
            .distinctUntilChanged()
            .flatMapLatest { query ->
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

    fun onSearchTypeSelectedChange(newType: Int) {
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
        return if (hours != 0) "${hours}h ${remainingMinutes}m"
        else "${remainingMinutes}m"
    }

    fun formatPrice(value: Long): String {
        val formatter = NumberFormat.getInstance(Locale("es", "AR"))
        return formatter.format(value)
    }

    fun clearRatingResponse() {
        _rating.value = null
    }

    // MOVIES

    fun getMovieDetailsById(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieDetails.value = getMovieDetailsByIdUseCase(movieId, defaultLanguage, defaultCountry)
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
            _movieRecommendations.value = getRecommendationsByIdUseCase(movieId, defaultLanguage)
        }
    }

    fun getReviewsById(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _reviews.value = getReviewsByIdUseCase(movieId)
        }
    }

    fun getVideosById(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieVideos.value = getVideosByIdUseCase(movieId, defaultLanguage)
        }
    }

    // COLLECTIONS

    fun getCollectionDetailsById(collectionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _collectionDetails.value = getCollectionDetailsByIdUseCase(collectionId, defaultLanguage)
        }
    }

    // SERIES

    fun getSeriesDetailsById(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _seriesDetails.value = getSeriesDetailsByIdUseCase(seriesId, defaultLanguage)
        }
    }

    fun getSeasonDetailsById(seriesId: Int, seasonNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _seasonsDetails.value = getSeasonDetailsUseCase(seriesId, seasonNumber, defaultLanguage)
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
            _seriesVideos.value = getSeriesVideosByIdUseCase(seriesId, defaultLanguage)
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
            _episodeDetails.value =
                getEpisodeDetailsByIdUseCase(seriesId, seasonNumber, episodeNumber, defaultLanguage)
        }
    }

    fun getSeriesName(seriesId: Int): String {
        viewModelScope.launch(Dispatchers.IO) {
            _seriesDetails.value = getSeriesDetailsByIdUseCase(seriesId, defaultLanguage)
        }
        return _seriesDetails.value?.name.toString()
    }

    // PEOPLE

    fun getPeopleDetailsById(personId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _peopleDetails.value = getPeopleDetailsByIdUseCase(personId, defaultLanguage)
        }
    }

    fun getPeopleMovieInterpretationsById(personId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _peopleMovieInterpretations.value = getPeopleMovieInterpretationsByIdUseCase(personId, defaultLanguage)
        }
    }

    fun getPeopleSeriesInterpretationsById(personId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _peopleSeriesInterpretations.value = getPeopleSeriesInterpretationsByIdUseCase(personId, defaultLanguage)
        }
    }

    fun getPeopleMediaById(personId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _peopleMediaImages.value = getPeopleMediaByIdUseCase(personId)
        }
    }

    // RATING

    fun rateMovie(value: Float, movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _rating.value = rateMovieUseCase(rating = value, movieId = movieId)
        }
    }

    fun deleteRateMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _rating.value = deleteRateMovieUseCase(movieId)
        }
    }

    fun rateSeries(value: Float, seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _rating.value = rateSeriesUseCase(rating = value, seriesId = seriesId)
        }
    }

    fun deleteRateSeries(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _rating.value = deleteRateSeriesUseCase(seriesId)
        }
    }

    fun rateSeriesEpisodes(value: Float, seriesId: Int, episodeNumber: Int, seasonNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _rating.value = rateSeriesEpisodeUseCase(
                rating = value,
                seriesId = seriesId,
                episodeNumber = episodeNumber,
                seasonNumber = seasonNumber
            )
        }
    }

    fun deleteRateSeriesEpisodes(seriesId: Int, episodeNumber: Int, seasonNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _rating.value = deleteRateSeriesEpisodeUseCase(
                seriesId = seriesId,
                episodeNumber = episodeNumber,
                seasonNumber = seasonNumber
            )
        }
    }

    fun isMovieRated(movieId: Int): Boolean {
        return _ratedMovies.value?.any { it.id == movieId } == true
    }

    fun isSeriesRated(seriesId: Int): Boolean {
        return _ratedSeries.value?.any { it.id == seriesId } == true
    }

    fun isEpisodeRated(seriesId: Int, episodeNumber: Int, seasonNumber: Int): Boolean {
        return _ratedSeriesEpisodes.value?.any { it.showId == seriesId && it.episodeNumber == episodeNumber && it.seasonNumber == seasonNumber } == true
    }

    fun updateRatedMovies() {
        getRatedMovies()
    }

    fun updateRatedSeries() {
        getRatedSeries()
    }

    fun updateRatedEpisodes(){
        getRatedSeriesEpisodes()
    }

    fun getMyMovieRate(movieId: Int): Float {
        return _ratedMovies.value?.find { it.id == movieId }?.rating ?: 0f
    }

    fun getMySeriesRate(seriesId: Int): Float {
        return _ratedSeries.value?.find { it.id == seriesId }?.rating ?: 0f
    }

    fun getMyEpisodeRate(seriesId: Int, episodeNumber: Int, seasonNumber: Int): Float {
        return _ratedSeriesEpisodes.value?.find { it.showId == seriesId && it.episodeNumber == episodeNumber && it.seasonNumber == seasonNumber }?.rating ?: 0f
    }

    fun getTotalRatingCount() {
        viewModelScope.launch(Dispatchers.IO) {
            _totalRatingCount.value = getRatedCountUseCase()
        }
    }

    // ACCOUNT

    fun getRatedMovies(accountId: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            _ratedMovies.value = getRatedMoviesUseCase()
        }
    }

    fun getRatedSeries(accountId: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            _ratedSeries.value = getRatedSeriesUseCase(accountId)
        }
    }

    fun getRatedSeriesEpisodes(accountId: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            _ratedSeriesEpisodes.value = getRatedEpisodesUseCase(accountId)
        }
    }

    fun onAddFavorite(mediaId: Int, mediaType: String, favorite: Boolean, accountId: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            _addFavoriteRequest.value = addFavoriteUseCase(mediaId, mediaType, favorite, accountId)
        }
    }

    fun clearFavoriteRequest() {
        _addFavoriteRequest.value = null
    }

    fun updateFavoritesMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoritesMovies.value = getFavoritesMoviesUseCase(0)
        }
    }

    fun updateFavoritesSeries() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoritesSeries.value = getFavoritesSeriesUseCase(0)
        }
    }

    fun movieIsFavorite(movieId: Int): Boolean {
        return _favoritesMovies.value?.any { it.id == movieId } == true
    }

    fun seriesIsFavorite(seriesId: Int): Boolean {
        return _favoritesSeries.value?.any { it.id == seriesId } == true
    }

    fun getFavoritesCount(accountId: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            _favoritesCount.value = getFavoritesCountUseCase(accountId)
        }
    }

    fun onAddToWatchlist(mediaId: Int, mediaType: String, watchList: Boolean, accountId: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            _watchListRequest.value =
                addToWatchlistUseCase(mediaId, mediaType, watchList, accountId)
        }
    }

    fun clearWatchlistRequest() {
        _watchListRequest.value = null
    }

    fun movieIsInWatchlist(movieId: Int): Boolean {
        return _watchlistMovies.value?.any { it.id == movieId } == true
    }

    fun seriesIsInWatchlist(seriesId: Int): Boolean {
        return _watchlistSeries.value?.any { it.id == seriesId } == true
    }

    fun getWatchlistMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _watchlistMovies.value = getWatchlistMoviesUseCase(0)
        }
    }

    fun getWatchlistSeries() {
        viewModelScope.launch(Dispatchers.IO) {
            _watchlistSeries.value = getWatchlistSeriesUseCase(0)
        }
    }

    fun getMyLists(accountId: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            _myLists.value = getMyListsUseCase(accountId)
        }
    }

    fun clearWatchListRequest() {
        _watchListRequest.value = null
    }

    fun createList(accountId: Int = 0, name: String, description: String, language: String = "es") {
        viewModelScope.launch(Dispatchers.IO) {
            _createListRequest.value = createListUseCase(accountId, name, description, language)
        }
    }

    fun clearCreateListRequest() {
        _createListRequest.value = null
    }

    fun deleteList(listId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _deleteListRequest.value = deleteListUseCase(listId)
        }
    }

    fun clearDeleteListRequest() {
        _deleteListRequest.value = null
    }

    fun deleteItemFromList(listId: Int, itemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _deleteItemFromListRequest.value =
                deleteItemFromListUseCase(listId = listId, itemId = itemId)
        }
    }

    fun clearDeleteItemFromListRequest() {
        _deleteItemFromListRequest.value = null
    }

    fun clearList(listId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _clearListRequest.value = clearListUseCase(listId)
        }
    }

    fun clearListRequest(){
        _clearListRequest.value = null
    }

    fun getListDetails(listId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _listDetails.value = getListDetailsUseCase(listId)
        }
    }

    // PROVIDERS

    fun getMovieProvidersByMovieId(movieId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieProviders.value = getMovieProvidersByMovieIdUseCase(movieId)
        }
    }

    fun getMovieProvidersByRegion(): TypeProvider? {
        return _movieProviders.value?.providers?.get(defaultCountry)
    }

}

