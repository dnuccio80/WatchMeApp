package com.example.watchme.app.domain.movies

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.data.network.responses.MovieResponse
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import org.intellij.lang.annotations.Language
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(language: String, region:String): List<MovieDataClass> {
        return apiRepository.getNowPlayingMovies(language, region)
    }
}