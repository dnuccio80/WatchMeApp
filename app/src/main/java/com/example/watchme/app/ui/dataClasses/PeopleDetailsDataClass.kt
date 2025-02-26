package com.example.watchme.app.ui.dataClasses

data class PeopleDetailsDataClass(
    val name:String,
    val biography:String,
    val birthday:String,
    val deathDay:String?,
    val homepage:String?,
    val id:Int,
    val knownForDepartment:String,
    val placeOfBirth:String?,
    val profilePath:String?,
)

data class PeopleMovieInterpretationDataClass(
    val cast: List<MovieDataClass>,
    val crew: List<MovieDataClass>,
)

data class PeopleSeriesInterpretationDataClass(
    val cast: List<SeriesDataClass>,
    val crew: List<SeriesDataClass>,
)
