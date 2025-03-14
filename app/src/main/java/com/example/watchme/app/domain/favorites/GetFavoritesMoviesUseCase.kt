package com.example.watchme.app.domain.favorites

import com.example.watchme.app.data.network.ApiRepository
import javax.inject.Inject

class GetFavoritesMoviesUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(accountId:Int) = apiRepository.getFavoritesMovies(accountId)
}