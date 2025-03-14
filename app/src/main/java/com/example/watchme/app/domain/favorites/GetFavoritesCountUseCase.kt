package com.example.watchme.app.domain.favorites

import com.example.watchme.app.data.network.ApiRepository
import javax.inject.Inject

class GetFavoritesCountUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(accountId: Int) : Int {

        val movieCount: Int = apiRepository.getFavoritesMovies(accountId).count()
        val seriesCount: Int = apiRepository.getFavoritesSeries(accountId).count()
        val result = movieCount + seriesCount

        return result

    }

}