package com.example.watchme.app.domain.people

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.PeopleMovieInterpretationDataClass
import javax.inject.Inject

class GetPeopleMovieInterpretationsByIdUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(personId: Int, language: String) : PeopleMovieInterpretationDataClass = apiRepository.getPeopleMovieInterpretationsById(personId, language)
}