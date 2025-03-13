package com.example.watchme.app.domain.account

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import com.example.watchme.app.ui.dataClasses.RatedItemDataClass
import javax.inject.Inject

class GetRatedMoviesUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(accountId: Int = 0) : List<RatedItemDataClass> = apiRepository.getRatedMovies(accountId)
}