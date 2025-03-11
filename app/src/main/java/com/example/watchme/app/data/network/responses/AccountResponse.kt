package com.example.watchme.app.data.network.responses

import android.accounts.Account
import com.example.watchme.app.ui.dataClasses.AccountDetailsDataClass
import com.google.gson.annotations.SerializedName

data class AccountResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("iso_639_1") val isoLocation: String,
    @SerializedName("iso_3166_1") val isoCountry: String,
    @SerializedName("name") val name: String,
    @SerializedName("include_adult") val includeAdult: Boolean,
    @SerializedName("username") val username: String,
)

fun AccountResponse.toAccountDetailsDataClass(): AccountDetailsDataClass {
    return AccountDetailsDataClass(
        id = id,
        isoLocation = isoLocation,
        isoCountry = isoCountry,
        name = name,
        includeAdult = includeAdult,
        username = username
    )
}