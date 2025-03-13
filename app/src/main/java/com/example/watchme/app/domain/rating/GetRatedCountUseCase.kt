package com.example.watchme.app.domain.rating

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.TotalRatedResultsDataClass
import javax.inject.Inject

class GetRatedCountUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(accountId: Int = 0): TotalRatedResultsDataClass {
        val moviesCount: Int = apiRepository.getRatedMoviesCount(accountId).totalResults
        val seriesCount = apiRepository.getRatedSeriesCount(accountId).totalResults
        val episodesCount: Int = apiRepository.getRatedEpisodesCount(accountId).totalResults
        val totalResult = seriesCount + moviesCount + episodesCount

        return TotalRatedResultsDataClass(
            totalResults = totalResult
        )
    }
}