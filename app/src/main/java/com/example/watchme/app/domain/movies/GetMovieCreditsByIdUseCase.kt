package com.example.watchme.app.domain.movies

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.MovieCreditsDataClass
import javax.inject.Inject

class GetMovieCreditsByIdUseCase @Inject constructor(private val apiRepository: ApiRepository){
    suspend operator fun invoke(movieId: Int) : MovieCreditsDataClass = apiRepository.getMovieCreditsById(movieId)
}