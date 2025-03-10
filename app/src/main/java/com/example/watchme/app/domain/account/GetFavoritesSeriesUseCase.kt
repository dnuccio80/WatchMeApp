package com.example.watchme.app.domain.account

import com.example.watchme.app.data.network.ApiRepository
import javax.inject.Inject

class GetFavoritesSeriesUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(accountId:Int) = apiRepository.getFavoritesSeries(accountId)
}