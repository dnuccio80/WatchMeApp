package com.example.watchme.app.domain.searches

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.SearchDataClass
import javax.inject.Inject

class GetSearchMovieUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(query: String, language: String, region: String): List<SearchDataClass> =
        apiRepository.getSearchMovies(query, language, region)

}