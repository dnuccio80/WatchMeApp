package com.example.watchme.app.domain.movies

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.DetailsMovieDataClass
import org.intellij.lang.annotations.Language
import javax.inject.Inject

class GetMovieDetailsByIdUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(movieId:Int, language: String, region: String) : DetailsMovieDataClass = apiRepository.getMovieDetailsById(movieId, language, region)
}