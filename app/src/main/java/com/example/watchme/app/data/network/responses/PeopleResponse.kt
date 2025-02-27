package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.BackdropImageDataClass
import com.example.watchme.app.ui.dataClasses.PeopleDetailsDataClass
import com.example.watchme.app.ui.dataClasses.PeopleMovieInterpretationDataClass
import com.example.watchme.app.ui.dataClasses.PeopleSeriesInterpretationDataClass
import com.google.gson.annotations.SerializedName

data class PeopleDetailsResponse(
    @SerializedName("name") val name:String,
    @SerializedName("biography") val biography:String,
    @SerializedName("birthday") val birthday:String,
    @SerializedName("deathday") val deathDay:String,
    @SerializedName("homepage") val homepage:String,
    @SerializedName("id") val id:Int,
    @SerializedName("known_for_department") val knownForDepartment:String,
    @SerializedName("place_of_birth") val placeOfBirth:String,
    @SerializedName("profile_path") val profilePath:String,
)

fun PeopleDetailsResponse.toPeopleDetailsDataClass(): PeopleDetailsDataClass {
    return PeopleDetailsDataClass(
        name = name,
        biography = biography,
        birthday = birthday,
        deathDay = deathDay,
        homepage = homepage,
        id = id,
        knownForDepartment = knownForDepartment,
        placeOfBirth = placeOfBirth,
        profilePath = profilePath,
    )
}

data class PeopleMovieInterpretationResponse (
    @SerializedName("cast") val cast: List<Movie>,
    @SerializedName("crew") val crew: List<Movie>,
)

fun PeopleMovieInterpretationResponse.toPeopleMoviesInterpretationDataClass(): PeopleMovieInterpretationDataClass {
    return PeopleMovieInterpretationDataClass(
        cast = cast.map { it.toMovieDataClass() },
        crew = crew.map { it.toMovieDataClass() }
    )
}

data class PeopleSeriesInterpretationResponse (
    @SerializedName("cast") val cast: List<Series>,
    @SerializedName("crew") val crew: List<Series>,
)

fun PeopleSeriesInterpretationResponse.toPeopleSeriesInterpretationDataClass(): PeopleSeriesInterpretationDataClass {
    return PeopleSeriesInterpretationDataClass(
        cast = cast.map { it.toSeriesDataClass() },
        crew = crew.map { it.toSeriesDataClass() }
    )

}

data class ImagePeopleResponse(
    @SerializedName("profiles") val profiles: List<PeopleProfile>
)

fun ImagePeopleResponse.toBackdropImageDataClass() : List<BackdropImageDataClass> {
    return profiles.map {
        BackdropImageDataClass(
            filePath = it.filePath
        )
    }
}

data class PeopleProfile(
    @SerializedName("file_path") val filePath: String
)