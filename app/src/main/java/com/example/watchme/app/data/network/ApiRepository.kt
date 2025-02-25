package com.example.watchme.app.data.network

import com.example.watchme.app.data.network.responses.toBackdropImageDataClass
import com.example.watchme.app.data.network.responses.toDetailsMovieDataClass
import com.example.watchme.app.data.network.responses.toMovieCreditsDataClass
import com.example.watchme.app.data.network.responses.toMovieDataClass
import com.example.watchme.app.data.network.responses.toProvidersDataClass
import com.example.watchme.app.data.network.responses.toReviewDataClass
import com.example.watchme.app.data.network.responses.toSeasonDetailsDataClass
import com.example.watchme.app.data.network.responses.toSeriesDataClass
import com.example.watchme.app.data.network.responses.toSeriesDetailsDataClass
import com.example.watchme.app.data.network.responses.toVideoDataClass
import com.example.watchme.app.ui.dataClasses.BackdropImageDataClass
import com.example.watchme.app.ui.dataClasses.DetailsMovieDataClass
import com.example.watchme.app.ui.dataClasses.EpisodeDetailsDataClass
import com.example.watchme.app.ui.dataClasses.MovieCreditsDataClass
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import com.example.watchme.app.ui.dataClasses.ProvidersDataClass
import com.example.watchme.app.ui.dataClasses.ReviewDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDataClass
import com.example.watchme.app.ui.dataClasses.SeriesDetailsDataClass
import com.example.watchme.app.ui.dataClasses.VideoDataClass
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

    suspend fun getMovieCreditsById(movieId: Int): MovieCreditsDataClass {
        return apiService.getMovieCreditsById(movieId).toMovieCreditsDataClass()
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

    suspend fun getSeasonsDetails(seriesId: Int, seasonNumber: Int): List<EpisodeDetailsDataClass> {
        return apiService.getSeasonDetailsById(seriesId, seasonNumber).toSeasonDetailsDataClass()
    }

    suspend fun getSeriesRecommendationsById(seriesId: Int) : List<SeriesDataClass> {
        return apiService.getSeriesRecommendationsById(seriesId).toSeriesDataClass()
    }

    suspend fun getSeriesImageListById(seriesId: Int): List<BackdropImageDataClass> {
        return apiService.getSeriesImageListById(seriesId).toBackdropImageDataClass()
    }

    suspend fun getSeriesVideosById(seriesId: Int): List<VideoDataClass> {
        return apiService.getSeriesVideosById(seriesId).toVideoDataClass()
    }

}