package com.example.watchme.app.domain.providers

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.data.network.responses.MovieProvidersResponse
import javax.inject.Inject

class GetMovieProvidersByMovieIdUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(movieId:Int) : MovieProvidersResponse = apiRepository.getMovieProvidersByMovieId(movieId)

}