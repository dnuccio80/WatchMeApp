package com.example.watchme.app.domain.lists

import com.example.watchme.app.data.network.ApiRepository
import javax.inject.Inject

class GetWatchlistMoviesUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(accountId: Int) = apiRepository.getWatchlistMovies(accountId)
}