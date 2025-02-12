package com.example.watchme.app.data.network.responses

import com.example.watchme.app.ui.dataClasses.ProvidersDataClass
import com.google.gson.annotations.SerializedName


data class ProvidersResponse(
    @SerializedName("results") val providers: List<Provider>
)

data class Provider(
    @SerializedName("logo_path") val logo: String,
    @SerializedName("provider_name") val providerName: String,
    @SerializedName("provider_id") val providerId: Int
)

fun ProvidersResponse.toProvidersDataClass(): List<ProvidersDataClass> {
    return providers.map { provider ->
        ProvidersDataClass(
            logo = provider.logo,
            providerName = provider.providerName,
            providerId = provider.providerId
        )
    }
}