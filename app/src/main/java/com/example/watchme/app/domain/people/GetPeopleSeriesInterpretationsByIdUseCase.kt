package com.example.watchme.app.domain.people

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.data.network.responses.toPeopleSeriesInterpretationDataClass
import com.example.watchme.app.ui.dataClasses.PeopleMovieInterpretationDataClass
import com.example.watchme.app.ui.dataClasses.PeopleSeriesInterpretationDataClass
import javax.inject.Inject

class GetPeopleSeriesInterpretationsByIdUseCase @Inject constructor(private val apiRepository: ApiRepository)  {
    suspend operator fun invoke(personId:Int, language: String) : PeopleSeriesInterpretationDataClass = apiRepository.getPeopleSeriesInterpretationsById(personId, language)
}