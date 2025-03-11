package com.example.watchme.app.data.network

import com.example.watchme.app.data.network.responses.toAccountDetailsDataClass
import com.example.watchme.app.data.network.responses.toBackdropImageDataClass
import com.example.watchme.app.data.network.responses.toCollectionDataClass
import com.example.watchme.app.data.network.responses.toSearchDataClass
import com.example.watchme.app.data.network.responses.toDetailsMovieDataClass
import com.example.watchme.app.data.network.responses.toCreditsDataClass
import com.example.watchme.app.data.network.responses.toEpisodesDetailsDataClass
import com.example.watchme.app.data.network.responses.toEpisodesRatedDataClass
import com.example.watchme.app.data.network.responses.toFavoriteDataClass
import com.example.watchme.app.data.network.responses.toMovieDataClass
import com.example.watchme.app.data.network.responses.toPeopleDetailsDataClass
import com.example.watchme.app.data.network.responses.toPeopleMoviesInterpretationDataClass
import com.example.watchme.app.data.network.responses.toPeopleSeriesInterpretationDataClass
import com.example.watchme.app.data.network.responses.toProvidersDataClass
import com.example.watchme.app.data.network.responses.toRatingDataClass
import com.example.watchme.app.data.network.responses.toReviewDataClass
import com.example.watchme.app.data.network.responses.toSeasonDetailsDataClass
import com.example.watchme.app.data.network.responses.toSeriesDataClass
import com.example.watchme.app.data.network.responses.toSeriesDetailsDataClass
import com.example.watchme.app.data.network.responses.toVideoDataClass
import com.example.watchme.app.data.network.responses.toWatchListDataClass
import com.example.watchme.app.ui.dataClasses.AccountDetailsDataClass
import com.example.watchme.app.ui.dataClasses.BackdropImageDataClass
import com.example.watchme.app.ui.dataClasses.CollectionDetailsDataClass
import com.example.watchme.app.ui.dataClasses.SearchDataClass
import com.example.watchme.app.ui.dataClasses.DetailsMovieDataClass
import com.example.watchme.app.ui.dataClasses.EpisodeDetailsDataClass
import com.example.watchme.app.ui.dataClasses.CreditsDataClass
import com.example.watchme.app.ui.dataClasses.EpisodesDetailsDataClass
import com.example.watchme.app.ui.dataClasses.EpisodesRatedDataClass
import com.example.watchme.app.ui.dataClasses.FavoriteDataClass
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import com.example.watchme.app.ui.dataClasses.PeopleDetailsDataClass
import com.example.watchme.app.ui.dataClasses.PeopleMovieInterpretationDataClass
import com.example.watchme.app.ui.dataClasses.PeopleSeriesInterpretationDataClass
import com.example.watchme.app.ui.dataClasses.ProvidersDataClass
import com.example.watchme.app.ui.dataClasses.RatingDataClass
import com.example.watchme.app.ui.dataClasses.ReviewDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDetailsDataClass
import com.example.watchme.app.ui.dataClasses.VideoDataClass
import com.example.watchme.app.ui.dataClasses.WatchListDataClass
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) {

    // MOVIES

    suspend fun getPopularMovies(): List<MovieDataClass> {
        return apiService.getPopularMovies().toMovieDataClass()
    }

    suspend fun getNowPlayingMovies(): List<MovieDataClass> {
        return apiService.getNowPlayingMovies().toMovieDataClass()
    }

    suspend fun getTopRatedMovies(): List<MovieDataClass> {
        return apiService.getTopRatedMovies().toMovieDataClass()
    }

    suspend fun getUpcomingMovies(): List<MovieDataClass> {
        return apiService.getUpcomingMovies().toMovieDataClass()
    }

    suspend fun getProviders(): List<ProvidersDataClass> {
        return apiService.getProviders().toProvidersDataClass()
    }

    suspend fun getMovieDetailsById(movieId: Int): DetailsMovieDataClass {
        return apiService.getMovieDetailsById(movieId).toDetailsMovieDataClass()
    }

    suspend fun getMovieCreditsById(movieId: Int): CreditsDataClass {
        return apiService.getMovieCreditsById(movieId).toCreditsDataClass()
    }

    suspend fun getMovieImageListById(movieId: Int): List<BackdropImageDataClass> {
        return apiService.getMovieImageListById(movieId).toBackdropImageDataClass()
    }

    suspend fun getRecommendationsById(movieId: Int): List<MovieDataClass> {
        return apiService.getRecommendationsById(movieId).toMovieDataClass()
    }

    suspend fun getReviewsById(movieId: Int): List<ReviewDataClass> {
        return apiService.getReviewsById(movieId).toReviewDataClass()
    }

    suspend fun getMovieVideosById(movieId: Int): List<VideoDataClass> {
        return apiService.getMovieVideosById(movieId).toVideoDataClass()
    }

    // COLLECTIONS

    suspend fun getCollectionDetailsById(collectionId: Int): CollectionDetailsDataClass {
        return apiService.getCollectionDetailsById(collectionId).toCollectionDataClass()
    }


    // SERIES

    suspend fun getPopularSeries(): List<SeriesDataClass> {
        return apiService.getPopularSeries().toSeriesDataClass()
    }

    suspend fun getAiringSeriesToday(): List<SeriesDataClass> {
        return apiService.getAiringSeriesToday().toSeriesDataClass()
    }

    suspend fun getOnTheAirSeries(): List<SeriesDataClass> {
        return apiService.getOnTheAirSeries().toSeriesDataClass()
    }

    suspend fun getTopRatedSeries(): List<SeriesDataClass> {
        return apiService.getTopRatedSeries().toSeriesDataClass()
    }

    suspend fun getSeriesDetailsById(seriesId: Int): SeriesDetailsDataClass {
        return apiService.getSeriesDetailsById(seriesId).toSeriesDetailsDataClass()
    }

    suspend fun getSeasonsDetailsById(
        seriesId: Int,
        seasonNumber: Int
    ): List<EpisodeDetailsDataClass> {
        return apiService.getSeasonDetailsById(seriesId, seasonNumber).toSeasonDetailsDataClass()
    }

    suspend fun getSeriesRecommendationsById(seriesId: Int): List<SeriesDataClass> {
        return apiService.getSeriesRecommendationsById(seriesId).toSeriesDataClass()
    }

    suspend fun getSeriesImageListById(seriesId: Int): List<BackdropImageDataClass> {
        return apiService.getSeriesImageListById(seriesId).toBackdropImageDataClass()
    }

    suspend fun getSeriesVideosById(seriesId: Int): List<VideoDataClass> {
        return apiService.getSeriesVideosById(seriesId).toVideoDataClass()
    }

    suspend fun getSeriesCreditsById(seriesId: Int): CreditsDataClass {
        return apiService.getSeriesCreditsById(seriesId).toCreditsDataClass()
    }

    // PEOPLE

    suspend fun getPeopleDetailsById(personId: Int): PeopleDetailsDataClass {
        return apiService.getPeopleDetails(personId).toPeopleDetailsDataClass()
    }

    suspend fun getPeopleMovieInterpretationsById(personId: Int): PeopleMovieInterpretationDataClass {
        return apiService.getPeopleMovieInterpretationsById(personId)
            .toPeopleMoviesInterpretationDataClass()
    }

    suspend fun getPeopleSeriesInterpretationsById(personId: Int): PeopleSeriesInterpretationDataClass {
        return apiService.getPeopleSeriesInterpretationsById(personId)
            .toPeopleSeriesInterpretationDataClass()
    }

    suspend fun getPeopleMediaById(personId: Int): List<BackdropImageDataClass> {
        return apiService.getPeopleMediaById(personId).toBackdropImageDataClass()
    }

    suspend fun getEpisodesDetailsById(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): EpisodesDetailsDataClass {
        return apiService.getEpisodesDetailsById(seriesId, seasonNumber, episodeNumber)
            .toEpisodesDetailsDataClass()
    }

    // SEARCHES

    suspend fun getSearchCollection(query: String): List<SearchDataClass> {
        return apiService.getSearchCollection(query).toSearchDataClass()
    }

    suspend fun getSearchMovies(query: String): List<SearchDataClass> {
        return apiService.getSearchMovies(query).toSearchDataClass()
    }

    suspend fun getSearchSeries(query: String): List<SearchDataClass> {
        return apiService.getSearchSeries(query).toSearchDataClass()
    }

    suspend fun getSearchPeople(query: String): List<SearchDataClass> {
        return apiService.getSearchPeople(query).toSearchDataClass()
    }

    // RATING

    suspend fun rateMovie(rating: Float, movieId: Int): RatingDataClass {
        return apiService.rateMovie(rating = rating, movieId = movieId).toRatingDataClass()
    }

    suspend fun deleteRateMovie(movieId: Int): RatingDataClass {
        return apiService.deleteRateMovie(movieId).toRatingDataClass()
    }

    suspend fun rateSeries(rating: Float, seriesId: Int): RatingDataClass {
        return apiService.rateSeries(rating = rating, seriesId = seriesId).toRatingDataClass()
    }

    suspend fun deleteRateSeries(seriesId: Int): RatingDataClass {
        return apiService.deleteRateSeries(seriesId = seriesId).toRatingDataClass()
    }

    suspend fun rateSeriesEpisodes(
        rating: Float,
        seriesId: Int,
        episodeNumber: Int,
        seasonNumber: Int
    ): RatingDataClass {
        return apiService.rateSeriesEpisodes(
            rating = rating,
            seriesId = seriesId,
            episodeNumber = episodeNumber,
            seasonNumber = seasonNumber
        ).toRatingDataClass()
    }

    suspend fun deleteRateSeriesEpisodes(
        seriesId: Int,
        episodeNumber: Int,
        seasonNumber: Int
    ): RatingDataClass {
        return apiService.deleteRateSeriesEpisodes(
            seriesId = seriesId,
            seasonNumber = seasonNumber,
            episodeNumber = episodeNumber
        ).toRatingDataClass()
    }

    suspend fun getRatedMovies(accountId: Int = 0): List<MovieDataClass> {
        return apiService.getRatedMovies(accountId = accountId).toMovieDataClass()
    }

    suspend fun getRatedSeries(accountId: Int = 0): List<SeriesDataClass> {
        return apiService.getRatedSeries(accountId = accountId).toSeriesDataClass()
    }

    suspend fun getRatedSeriesEpisodes(accountId: Int = 0): List<EpisodesRatedDataClass> {
        return apiService.getRatedSeriesEpisodes(accountId = accountId).toEpisodesRatedDataClass()
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
    ): WatchListDataClass {
        return apiService.addToWatchList(
            mediaId = mediaId,
            mediaType = mediaType,
            watchlist = watchlist,
            accountId = accountId
        ).toWatchListDataClass()
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


}