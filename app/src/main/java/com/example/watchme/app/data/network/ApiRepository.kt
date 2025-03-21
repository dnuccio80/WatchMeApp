package com.example.watchme.app.data.network

import com.example.watchme.app.data.network.responses.toAccountDetailsDataClass
import com.example.watchme.app.data.network.responses.toBackdropImageDataClass
import com.example.watchme.app.data.network.responses.toCollectionDataClass
import com.example.watchme.app.data.network.responses.toCreateListDataClass
import com.example.watchme.app.data.network.responses.toSearchDataClass
import com.example.watchme.app.data.network.responses.toDetailsMovieDataClass
import com.example.watchme.app.data.network.responses.toCreditsDataClass
import com.example.watchme.app.data.network.responses.toEpisodesDetailsDataClass
import com.example.watchme.app.data.network.responses.toEpisodesRatedDataClass
import com.example.watchme.app.data.network.responses.toFavoriteDataClass
import com.example.watchme.app.data.network.responses.toListDataClass
import com.example.watchme.app.data.network.responses.toListDetailsDataClass
import com.example.watchme.app.data.network.responses.toMediaProviderDataClass
import com.example.watchme.app.data.network.responses.toMovieDataClass
import com.example.watchme.app.data.network.responses.toPeopleDetailsDataClass
import com.example.watchme.app.data.network.responses.toPeopleMoviesInterpretationDataClass
import com.example.watchme.app.data.network.responses.toPeopleSeriesInterpretationDataClass
import com.example.watchme.app.data.network.responses.toProvidersDataClass
import com.example.watchme.app.data.network.responses.toRatedItemDataClass
import com.example.watchme.app.data.network.responses.toRatingRequestDataClass
import com.example.watchme.app.data.network.responses.toReviewDataClass
import com.example.watchme.app.data.network.responses.toSeasonDetailsDataClass
import com.example.watchme.app.data.network.responses.toSeriesDataClass
import com.example.watchme.app.data.network.responses.toSeriesDetailsDataClass
import com.example.watchme.app.data.network.responses.toTotalEpisodesRatedDataClass
import com.example.watchme.app.data.network.responses.toTotalResults
import com.example.watchme.app.data.network.responses.toVideoDataClass
import com.example.watchme.app.data.network.responses.toRequestResponseDataClass
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
import com.example.watchme.app.ui.dataClasses.MediaProviderDataClass
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
import com.example.watchme.app.ui.dataClasses.TotalEpisodesRatedDataClass
import com.example.watchme.app.ui.dataClasses.TotalRatedResultsDataClass
import com.example.watchme.app.ui.dataClasses.VideoDataClass
import com.example.watchme.app.ui.dataClasses.RequestResponseDataClass
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) {

    // MOVIES

    suspend fun getPopularMovies(language: String, region: String): List<MovieDataClass> {
        return apiService.getPopularMovies(language, region).toMovieDataClass()
    }

    suspend fun getNowPlayingMovies(language: String, region: String): List<MovieDataClass> {
        return apiService.getNowPlayingMovies(language, region).toMovieDataClass()
    }

    suspend fun getTopRatedMovies(language: String, region: String): List<MovieDataClass> {
        return apiService.getTopRatedMovies(language, region).toMovieDataClass()
    }

    suspend fun getUpcomingMovies(language: String, region: String): List<MovieDataClass> {
        return apiService.getUpcomingMovies(language, region).toMovieDataClass()
    }

    suspend fun getProviders(language: String, region: String): List<ProvidersDataClass> {
        return apiService.getProviders(language, region).toProvidersDataClass()
    }

    suspend fun getMovieDetailsById(
        movieId: Int,
        language: String,
        region: String
    ): DetailsMovieDataClass {
        return apiService.getMovieDetailsById(movieId, language, region).toDetailsMovieDataClass()
    }

    suspend fun getMovieCreditsById(movieId: Int): CreditsDataClass {
        return apiService.getMovieCreditsById(movieId).toCreditsDataClass()
    }

    suspend fun getMovieImageListById(movieId: Int): List<BackdropImageDataClass> {
        return apiService.getMovieImageListById(movieId).toBackdropImageDataClass()
    }

    suspend fun getRecommendationsById(movieId: Int, language: String): List<MovieDataClass> {
        return apiService.getRecommendationsById(movieId, language).toMovieDataClass()
    }

    suspend fun getReviewsById(movieId: Int): List<ReviewDataClass> {
        return apiService.getReviewsById(movieId).toReviewDataClass()
    }

    suspend fun getMovieVideosById(movieId: Int, language: String): List<VideoDataClass> {
        return apiService.getMovieVideosById(movieId, language).toVideoDataClass()
    }

    // COLLECTIONS

    suspend fun getCollectionDetailsById(
        collectionId: Int,
        language: String
    ): CollectionDetailsDataClass {
        return apiService.getCollectionDetailsById(collectionId, language).toCollectionDataClass()
    }


    // SERIES

    suspend fun getPopularSeries(language: String): List<SeriesDataClass> {
        return apiService.getPopularSeries(language).toSeriesDataClass()
    }

    suspend fun getAiringSeriesToday(language: String): List<SeriesDataClass> {
        return apiService.getAiringSeriesToday(language).toSeriesDataClass()
    }

    suspend fun getOnTheAirSeries(language: String): List<SeriesDataClass> {
        return apiService.getOnTheAirSeries(language).toSeriesDataClass()
    }

    suspend fun getTopRatedSeries(language: String): List<SeriesDataClass> {
        return apiService.getTopRatedSeries(language).toSeriesDataClass()
    }

    suspend fun getSeriesDetailsById(seriesId: Int, language: String): SeriesDetailsDataClass {
        return apiService.getSeriesDetailsById(seriesId, language).toSeriesDetailsDataClass()
    }

    suspend fun getSeasonsDetailsById(
        seriesId: Int,
        seasonNumber: Int, language: String
    ): List<EpisodeDetailsDataClass> {
        return apiService.getSeasonDetailsById(seriesId, seasonNumber, language)
            .toSeasonDetailsDataClass()
    }

    suspend fun getSeriesRecommendationsById(seriesId: Int): List<SeriesDataClass> {
        return apiService.getSeriesRecommendationsById(seriesId).toSeriesDataClass()
    }

    suspend fun getSeriesImageListById(seriesId: Int): List<BackdropImageDataClass> {
        return apiService.getSeriesImageListById(seriesId).toBackdropImageDataClass()
    }

    suspend fun getSeriesVideosById(seriesId: Int, language: String): List<VideoDataClass> {
        return apiService.getSeriesVideosById(seriesId, language).toVideoDataClass()
    }

    suspend fun getSeriesCreditsById(seriesId: Int): CreditsDataClass {
        return apiService.getSeriesCreditsById(seriesId).toCreditsDataClass()
    }

    // PEOPLE

    suspend fun getPeopleDetailsById(personId: Int, language: String): PeopleDetailsDataClass {
        return apiService.getPeopleDetails(personId, language).toPeopleDetailsDataClass()
    }

    suspend fun getPeopleMovieInterpretationsById(
        personId: Int,
        language: String
    ): PeopleMovieInterpretationDataClass {
        return apiService.getPeopleMovieInterpretationsById(personId, language)
            .toPeopleMoviesInterpretationDataClass()
    }

    suspend fun getPeopleSeriesInterpretationsById(
        personId: Int,
        language: String
    ): PeopleSeriesInterpretationDataClass {
        return apiService.getPeopleSeriesInterpretationsById(personId, language)
            .toPeopleSeriesInterpretationDataClass()
    }

    suspend fun getPeopleMediaById(personId: Int): List<BackdropImageDataClass> {
        return apiService.getPeopleMediaById(personId).toBackdropImageDataClass()
    }

    suspend fun getEpisodesDetailsById(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        language: String
    ): EpisodesDetailsDataClass {
        return apiService.getEpisodesDetailsById(seriesId, seasonNumber, episodeNumber, language)
            .toEpisodesDetailsDataClass()
    }

    // SEARCHES

    suspend fun getSearchCollection(
        query: String,
        language: String,
        region: String
    ): List<SearchDataClass> {
        return apiService.getSearchCollection(query, language, region).toSearchDataClass()
    }

    suspend fun getSearchMovies(
        query: String,
        language: String,
        region: String
    ): List<SearchDataClass> {
        return apiService.getSearchMovies(query, language, region).toSearchDataClass()
    }

    suspend fun getSearchSeries(
        query: String,
        language: String,
        region: String
    ): List<SearchDataClass> {
        return apiService.getSearchSeries(query, language, region).toSearchDataClass()
    }

    suspend fun getSearchPeople(
        query: String,
        language: String,
        region: String
    ): List<SearchDataClass> {
        return apiService.getSearchPeople(query, language, region).toSearchDataClass()
    }

    // RATING

    suspend fun rateMovie(rating: Float, movieId: Int): RatingRequestDataClass {
        return apiService.rateMovie(rating = rating, movieId = movieId).toRatingRequestDataClass()
    }

    suspend fun deleteRateMovie(movieId: Int): RatingRequestDataClass {
        return apiService.deleteRateMovie(movieId).toRatingRequestDataClass()
    }

    suspend fun rateSeries(rating: Float, seriesId: Int): RatingRequestDataClass {
        return apiService.rateSeries(rating = rating, seriesId = seriesId)
            .toRatingRequestDataClass()
    }

    suspend fun deleteRateSeries(seriesId: Int): RatingRequestDataClass {
        return apiService.deleteRateSeries(seriesId = seriesId).toRatingRequestDataClass()
    }

    suspend fun rateSeriesEpisodes(
        rating: Float,
        seriesId: Int,
        episodeNumber: Int,
        seasonNumber: Int
    ): RatingRequestDataClass {
        return apiService.rateSeriesEpisodes(
            rating = rating,
            seriesId = seriesId,
            episodeNumber = episodeNumber,
            seasonNumber = seasonNumber
        ).toRatingRequestDataClass()
    }

    suspend fun deleteRateSeriesEpisodes(
        seriesId: Int,
        episodeNumber: Int,
        seasonNumber: Int
    ): RatingRequestDataClass {
        return apiService.deleteRateSeriesEpisodes(
            seriesId = seriesId,
            seasonNumber = seasonNumber,
            episodeNumber = episodeNumber
        ).toRatingRequestDataClass()
    }

    suspend fun getRatedMovies(accountId: Int = 0): List<RatedItemDataClass> {
        return apiService.getRatedMovies(accountId = accountId).toRatedItemDataClass()
    }

    suspend fun getRatedSeries(accountId: Int = 0): List<RatedItemDataClass> {
        return apiService.getRatedSeries(accountId = accountId).toRatedItemDataClass()
    }

    suspend fun getRatedSeriesEpisodes(accountId: Int = 0): List<EpisodesRatedDataClass> {
        return apiService.getRatedSeriesEpisodes(accountId = accountId).toEpisodesRatedDataClass()
    }

    suspend fun getRatedMoviesCount(accountId: Int = 0): TotalRatedResultsDataClass {
        return apiService.getRatedMovies(accountId).toTotalResults()
    }

    suspend fun getRatedSeriesCount(accountId: Int = 0): TotalRatedResultsDataClass {
        return apiService.getRatedSeries(accountId).toTotalResults()
    }

    suspend fun getRatedEpisodesCount(accountId: Int = 0): TotalEpisodesRatedDataClass {
        return apiService.getRatedSeriesEpisodes(accountId).toTotalEpisodesRatedDataClass()
    }

    suspend fun addFavorites(
        mediaId: Int,
        mediaType: String,
        favorite: Boolean,
        accountId: Int
    ): FavoriteDataClass {
        return apiService.addFavorite(
            mediaId = mediaId,
            mediaType = mediaType,
            favorite = favorite,
            accountId = accountId
        ).toFavoriteDataClass()
    }

    suspend fun getFavoritesMovies(accountId: Int): List<MovieDataClass> {
        return apiService.getFavoritesMovies(accountId).toMovieDataClass()
    }

    suspend fun getFavoritesSeries(accountId: Int): List<SeriesDataClass> {
        return apiService.getFavoritesSeries(accountId).toSeriesDataClass()
    }

    suspend fun addToWatchlist(
        mediaId: Int,
        mediaType: String,
        watchlist: Boolean,
        accountId: Int
    ): RequestResponseDataClass {
        return apiService.addToWatchList(
            mediaId = mediaId,
            mediaType = mediaType,
            watchlist = watchlist,
            accountId = accountId
        ).toRequestResponseDataClass()
    }

    suspend fun getWatchlistMovies(accountId: Int): List<MovieDataClass> {
        return apiService.getWatchListMovies(accountId).toMovieDataClass()
    }

    suspend fun getWatchlistSeries(accountId: Int): List<SeriesDataClass> {
        return apiService.getWatchListSeries(accountId).toSeriesDataClass()
    }

    suspend fun getAccountDetails(accountId: Int): AccountDetailsDataClass {
        return apiService.getAccountDetails(accountId).toAccountDetailsDataClass()
    }

    suspend fun getMyLists(accountId: Int): List<ListDataClass> {
        return apiService.getMyLists(accountId).toListDataClass()
    }

    suspend fun createList(
        accountId: Int,
        name: String,
        description: String,
        language: String
    ): CreateListDataClass {
        return apiService.createList(
            accountId,
            name = name,
            description = description,
            language = language
        ).toCreateListDataClass()
    }

    suspend fun addMovieToList(listId: Int, mediaId: Int): RequestResponseDataClass {
        return apiService.addMovieToList(listId = listId, mediaId = mediaId).toRequestResponseDataClass()
    }

    suspend fun checkMovieOnList(listId: Int, mediaId: Int): RequestResponseDataClass {
        return apiService.checkMovieOnList(listId = listId, mediaId = mediaId).toRequestResponseDataClass()
    }

    suspend fun deleteList(listId: Int): RequestResponseDataClass {
        return apiService.deleteList(listId).toRequestResponseDataClass()
    }

    suspend fun getListDetails(listId: Int): ListDetailsDataClass {
        return apiService.getListDetails(listId).toListDetailsDataClass()
    }

    suspend fun deleteItemFromList(listId: Int, itemId: Int): RequestResponseDataClass {
        return apiService.deleteItemFromList(listId = listId, itemId = itemId)
            .toRequestResponseDataClass()
    }

    suspend fun clearList(listId: Int): RequestResponseDataClass {
        return apiService.clearList(listId).toRequestResponseDataClass()
    }

    // PROVIDERS

    suspend fun getMovieProvidersByMovieId(movieId: Int): MediaProviderDataClass {
        return apiService.getMovieProvidersByMovieId(movieId).toMediaProviderDataClass()
    }

    suspend fun getSeriesProvidersBySeriesId(seriesId: Int): MediaProviderDataClass {
        return apiService.getSeriesProvidersBySeriesId(seriesId).toMediaProviderDataClass()
    }

}