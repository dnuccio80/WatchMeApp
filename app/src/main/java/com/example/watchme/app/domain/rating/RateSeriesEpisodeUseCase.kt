package com.example.watchme.app.domain.rating

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.data.network.responses.RatingResponse
import com.example.watchme.app.ui.dataClasses.RatingDataClass
import javax.inject.Inject

class RateSeriesEpisodeUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(rating: Float, seriesId: Int, episodeNumber: Int, seasonNumber: Int): RatingDataClass {
        return apiRepository.rateSeriesEpisodes(rating = rating, seriesId = seriesId, episodeNumber = episodeNumber, seasonNumber = seasonNumber)
    }

}