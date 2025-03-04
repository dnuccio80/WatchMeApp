package com.example.watchme.app.domain.rating

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.RatingDataClass
import javax.inject.Inject

class DeleteRateSeriesEpisodeUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(
        seriesId: Int,
        episodeNumber: Int,
        seasonNumber: Int
    ): RatingDataClass = apiRepository.deleteRateSeriesEpisodes(
        seriesId = seriesId,
        episodeNumber = episodeNumber,
        seasonNumber = seasonNumber
    )
}