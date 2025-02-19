package com.example.watchme.app.domain.series

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.EpisodeDetailsDataClass
import javax.inject.Inject

class GetSeasonDetailsUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(seriesId: Int, seasonNumber: Int) : List<EpisodeDetailsDataClass> = apiRepository.getSeasonsDetails(seriesId, seasonNumber)
}