package com.example.watchme.app.domain.episodes

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.EpisodesDetailsDataClass
import javax.inject.Inject

class GetEpisodeDetailsByIdUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(seriesId: Int, seasonNumber: Int, episodeNumber: Int): EpisodesDetailsDataClass = apiRepository.getEpisodesDetailsById(seriesId, seasonNumber, episodeNumber)
}