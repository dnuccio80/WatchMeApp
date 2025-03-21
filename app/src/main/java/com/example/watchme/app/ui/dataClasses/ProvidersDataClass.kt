package com.example.watchme.app.ui.dataClasses

import com.example.watchme.app.data.network.responses.MediaProvider
import com.example.watchme.app.data.network.responses.TypeProviderResponse
import com.google.gson.annotations.SerializedName

data class ProvidersDataClass(
    val logo: String?,
    val providerName: String?,
    val providerId: Int?
)

data class MediaProviderDataClass(
    val providers: Map<String, TypeProviderDataClass?>?
)

data class TypeProviderDataClass(
    val buy: List<ProvidersDataClass?>?,
    val rent: List<ProvidersDataClass?>?
)

