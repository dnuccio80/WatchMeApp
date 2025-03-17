package com.example.watchme.app.domain.movies

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.data.network.responses.MovieResponse
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val apiRepository: ApiRepository){
    suspend operator fun invoke(language:String, country:String): List<MovieDataClass> {
        return apiRepository.getPopularMovies(language,country)
    }
}