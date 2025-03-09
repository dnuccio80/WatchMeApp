package com.example.watchme.app.domain.account

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.EpisodesRatedDataClass
import javax.inject.Inject

class GetRatedEpisodesUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(accountId:Int) : List<EpisodesRatedDataClass> = apiRepository.getRatedSeriesEpisodes(accountId)
}