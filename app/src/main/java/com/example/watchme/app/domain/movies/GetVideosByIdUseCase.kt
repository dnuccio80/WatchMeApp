package com.example.watchme.app.domain.movies

import com.example.watchme.app.data.network.ApiRepository
import javax.inject.Inject

class GetVideosByIdUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(movieId:Int) = apiRepository.getMovieVideosById(movieId)
}