package com.example.watchme.app.domain.movies

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(): List<MovieDataClass> = apiRepository.getUpcomingMovies()
}