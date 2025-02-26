package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.PeopleDetailsDataClass
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