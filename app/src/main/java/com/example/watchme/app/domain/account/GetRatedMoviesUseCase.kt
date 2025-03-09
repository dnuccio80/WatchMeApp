package com.example.watchme.app.domain.account

import com.example.watchme.app.data.network.ApiRepository
import javax.inject.Inject

class GetRatedMoviesUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(accountId: Int = 0) = apiRepository.getRatedMovies(accountId)
}